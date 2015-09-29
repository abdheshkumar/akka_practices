package akka.example

import akka.actor.{Actor, ActorRef, Props, ActorSystem}

import scala.concurrent.Future

/**
 * Created by abdhesh on 8/3/15.
 */
object Barista extends App {
  val system = ActorSystem("Barista")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  barista ! CappuccinoRequest
  barista ! EspressoRequest
  val customer = system.actorOf(Props(classOf[Customer], barista), "Customer")
  customer ! CaffeineWithdrawalWarning
  //barista ! ClosingTime
  println("I ordered a cappuccino and an espresso")
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  implicit val timeout = Timeout(2.second)
  implicit val ec = system.dispatcher
  val f: Future[Any] = barista ? CappuccinoRequest
  f.onSuccess {
    case Bill(cents) => println(s"Will pay $cents cents for a cappuccino")
  }
  system.shutdown()
}

sealed trait CoffeeRequest

case object CappuccinoRequest extends CoffeeRequest

case object EspressoRequest extends CoffeeRequest

case class Bill(cents: Int)

case object ClosingTime

class Barista extends Actor {
  def receive = {
    case CappuccinoRequest =>
      sender ! Bill(250)
      println("I have to prepare a cappuccino!")
    case EspressoRequest =>
      sender ! Bill(200)
      println("Let's prepare an espresso.")
    case ClosingTime => context.system.shutdown()
  }
}
