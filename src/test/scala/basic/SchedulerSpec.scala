package basic

import akka.actor.{Actor, ActorRef, Props, actorRef2Scala}
import akka.testkit.TestActorRef

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class SchedulerSpec extends AkkaSpec {

  describe("Akka scheduler") {
    it("continues to deliver to restarted actor") {
      val restartingActor = TestActorRef(Props(new SelfSendingActor(testActor)))
      restartingActor ! Restart
      fishForMessage() {
        case Response ⇒ true
      }
    }
  }
}

object MessageToSelf
object Response
object Restart
class SelfSendingActor(testActor: ActorRef) extends Actor {
  val scheduledMsg = context.system.scheduler.schedule(100 millis, 100 millis) {
    self ! MessageToSelf
  }
  def receive = {
    case MessageToSelf ⇒ testActor ! Response
    case Restart       ⇒ throw new Exception("provoke restart")
  }

  override def postStop = scheduledMsg.cancel()
}
