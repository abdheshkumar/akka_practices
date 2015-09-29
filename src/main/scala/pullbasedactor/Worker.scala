package pullbasedactor

import akka.actor.{ActorRef, Actor}
import pullbasedactor.WorkPullingPattern.{WorkAvailable, Work, RegisterWorker, GimmeWork}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by abdhesh on 9/5/15.
 */
abstract class Worker[T] (val master: ActorRef) extends Actor {
  override def preStart: Unit = {
    master ! RegisterWorker(self)
    master ! GimmeWork // keep working on actor restart
  }

  def receive = {
    case WorkAvailable => master ! GimmeWork
    case Work(work: T) => doWork(work) onComplete { case _ ⇒ master ! GimmeWork }
  }

  def doWork(work: T): Future[_]

}
