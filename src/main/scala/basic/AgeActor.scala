package basic

import akka.actor.{Props, Actor}

/**
 * Created by abdhesh on 7/23/15.
 */

case object AskAge

class AgeActor extends Actor {
  def receive = {
    case AskAge => sender ! "25 years old"
    case _ => println("Undefined")
  }
}

object AgeActor {
  def props = Props[AgeActor]
}

