package basic

import akka.actor._

/**
 * Created by abdhesh on 7/24/15.
 */
object MyActor {

  case class Greeting(from: String)

  case object Goodbye

}

class MyActor(myParent: ActorRef, greeting: String) extends Actor with ActorLogging {

  import MyActor._

  def receive = {
    case Greeting(greeter) => log.info(s"I was greeted by $greeter.")
    case Goodbye => log.info("Someone said goodbye to me.")
  }
}

class ActorWithArgs(name: String) extends Actor with ActorLogging {
  def receive = {
    case "Argument" => log.info(s"I was greeted by $name.")
  }
}

object MyActorApp extends App {
  val props1 = Props[MyActor]
  val props2 = Props(new ActorWithArgs("arg"))
  // careful, see below
  val props3 = Props(classOf[ActorWithArgs], "arg")
  val system = ActorSystem("")
}


import akka.actor.{Actor, ActorIdentity, Identify, Terminated}

class Follower extends Actor {
  val identifyId = 1
  context.actorSelection("/user/another") ! Identify(identifyId)



  def receive = {
    case ActorIdentity(`identifyId`, Some(ref)) =>
      context.watch(ref)
      context.become(active(ref))
    case ActorIdentity(`identifyId`, None) => context.stop(self)

  }

  def active(another: ActorRef): Actor.Receive = {
    case Terminated(`another`) => context.stop(self)
  }
}