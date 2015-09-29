package folttolerance

import akka.actor.{Props, Actor, OneForOneStrategy}
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
/**
  * Created by abdhesh on 8/1/15.
 */
class Supervisor extends Actor {

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  def receive = {
    case p: Props => sender() ! context.actorOf(p)
  }
}
