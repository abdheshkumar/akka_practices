package basic

import akka.actor.{Props, Actor, ActorLogging}

/**
 * Created by abdhesh on 7/23/15.
 */
class Person extends Actor with ActorLogging {

  import BookSeller._

  def receive = {
    case Book => log.info("It's the Akka Concurrency by Derek Wyatt")
      Thread.sleep(1000)
      log.info("What about the AKKA actors")
      sender ! WrongTitle
  }
}

object Person {
  def props = Props[Person]
}