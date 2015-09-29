package pullbasedactor

import akka.actor.ActorRef

/**
 * Created by abdhesh on 9/5/15.
 */
object WorkPullingPattern {

  sealed trait Message

  trait Epic[T] extends Iterable[T]

  //used by master to create work
  case object GimmeWork extends Message

  case object CurrentlyBusy extends Message

  case object WorkAvailable extends Message

  case class RegisterWorker(worker: ActorRef) extends Message

  case class Work[T](work: T) extends Message

}
