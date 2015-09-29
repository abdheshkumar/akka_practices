package folttolerance

import akka.actor.{Actor, ActorRef}
import scala.collection.immutable._

/**
 * Created by abdhesh on 8/1/15.
 */
object TestKitUsageSpec {
  // Define your test specific configuration here
  val config = """
    akka {
      loglevel = "WARNING"
    }
               """

  /**
   * An Actor that forwards every message to a next Actor
   */
  class ForwardingActor(next: ActorRef) extends Actor {
    def receive = {
      case msg => next ! msg
    }
  }

  /**
   * An Actor that only forwards certain messages to a next Actor
   */
  class FilteringActor(next: ActorRef) extends Actor {
    def receive = {
      case msg: String => next ! msg
      case _ => None
    }
  }

  /**
   * An actor that sends a sequence of messages with a random head list, an
   * interesting value and a random tail list. The idea is that you would
   * like to test that the interesting value is received and that you cant
   * be bothered with the rest
   */
  class SequencingActor(next: ActorRef, head: Seq[String],
                        tail: Seq[String]) extends Actor {
    def receive = {
      case msg => {
        head foreach {
          next ! _
        }
        next ! msg
        tail foreach {
          next ! _
        }
      }
    }
  }

}