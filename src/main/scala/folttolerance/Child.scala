package folttolerance

import akka.actor.Actor

/**
 * Created by abdhesh on 8/1/15.
 */
class Child extends Actor {
  var state = 0

  def receive = {
    case ex: Exception => throw ex
    case x: Int => state = x
    case "get" => sender() ! state
  }
}