package akkapatterns

import akka.actor.{Actor, Props}
import akka.pattern.ask
import basic.AkkaSpec
import pullbasedactor.WorkPullingPattern.Epic
import pullbasedactor.{Master, Worker}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
 * sample usage for work pulling pattern as described at
 *  http://www.michaelpollmeier.com/akka-work-pulling-pattern/
 */
class WorkPullingPatternEmailScenarioSpec extends AkkaSpec {
  type Work = String

  describe("email scenario") {
    val masterName = "emailCoordinator"
    val master = system.actorOf(Props[Master[Work]], masterName)

    case class Email(context: Work)
    case object SendResult
    trait MailServer { def send(email: Email) }
    val mailserver = mock[MailServer]

    val emailCreator = system.actorOf(Props(new Actor {
      def receive = {
        case context: Work ⇒ sender ! Email(context)
      }
    }))

    val emailSender = system.actorOf(Props(new Actor {
      def receive = {
        case email: Email ⇒
          mailserver.send(email)
          sender ! SendResult
      }
    }))

    // our worker implementation
    val emailCoordinator = system.actorOf(Props(new Worker[Work](master) {
      def doWork(context: Work): Future[_] =
        for {
          email ← emailCreator ? context
          result ← emailSender ? email
        } yield result
    }))

    it("sends out all the email") {
      val emailContexts = List("context1", "context2", "context3")
      val epic = new Epic[Work] { override val iterator = emailContexts.iterator }
      master ! epic

      val expectedEmails = emailContexts map { Email(_) }
      Thread.sleep(100)
      expectedEmails foreach { verify(mailserver).send(_) }
    }
  }
}

