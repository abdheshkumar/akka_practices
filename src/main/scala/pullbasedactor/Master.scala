package pullbasedactor

import akka.actor.{Actor, ActorRef, Terminated}
import org.slf4j.LoggerFactory
import pullbasedactor.WorkPullingPattern._

/**
 * Created by abdhesh on 9/5/15.
 */
class Master[T] extends Actor {
  val log = LoggerFactory.getLogger(getClass)
  val workers = scala.collection.mutable.Set.empty[ActorRef]
  var currentEpic: Option[Epic[String]] = None

  def receive = {
    case epic: Epic[String] =>
      if (currentEpic.isDefined) sender ! CurrentlyBusy
      else if (workers.isEmpty) log.error("Got work but there are no workers registered.")
      else {
        currentEpic = Some(epic)
        workers foreach (_ ! WorkAvailable)
      }
    case RegisterWorker(worker) =>
      log.info(s"worker $worker registered")
      context.watch(worker)
      workers += worker
    case Terminated(worker) =>
      log.info(s"worker $worker died - taking off the set of workers")
      workers.remove(worker)
    case GimmeWork => currentEpic match {
      case None =>
        log.info("workers asked for work but we've no more work to do")
      case Some(epic) =>
        val iter = epic.iterator
        if (iter.hasNext) sender ! Work(iter.next)
        else {
          log.info(s"done with current epic $epic")
          currentEpic = None
        }
    }
  }
}