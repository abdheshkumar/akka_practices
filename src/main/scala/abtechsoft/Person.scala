package abtechsoft

import akka.actor.{Props, ActorLogging, Actor}

/**
 * Created by abdhesh on 7/14/15.
 */
class Person extends Actor with ActorLogging {
  def receive = {
    case FullPint =>
      log.info("I'll make short work of this")

      Thread.sleep(1000)

      log.info("I'm ready for the next")

      sender ! EmptyPint
  }
}

object Person {
  def props = Props(new Person)
}