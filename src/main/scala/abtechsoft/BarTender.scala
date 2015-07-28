package abtechsoft

import akka.actor.{Props, ActorLogging, Actor}

/**
 * Created by abdhesh on 7/14/15.
 */
class BarTender extends Actor with ActorLogging {
  def receive = {
    case Ticket =>
      log.info("1 pint coming right up")

      Thread.sleep(1000)

      log.info("Your pint is ready, here you go")

      sender ! FullPint

    case EmptyPint =>
      log.info("I think you're done for the day")

      context.system.shutdown()
  }
}

object BarTender {
  def props = Props(new BarTender)
}
