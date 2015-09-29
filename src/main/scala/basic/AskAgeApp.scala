package basic

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern._

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

/**
 * Created by abdhesh on 7/23/15.
 */
object AskAgeApp extends App {
  val system = ActorSystem("AgeActorSystem")
  val ageActor = system.actorOf(AgeActor.props, name = "ageActor")
  implicit val timeout = Timeout(5 seconds)
  val future: Future[Any] = ageActor ? AskAge
  val age: Boolean = Await.result(future, timeout.duration).isInstanceOf[String]
  val future2: Future[String] = ask(ageActor, AskAge).mapTo[String]
  val age2: String = Await.result(future2, timeout.duration)
  println(age)
  println(age2)
  system.shutdown


}
