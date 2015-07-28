package abtechsoft

/**
 * Created by abdhesh on 7/14/15.
 */

import akka.actor.ActorSystem

case object Ticket

case object FullPint

case object EmptyPint


object HowdyAkka extends App {
  val system = ActorSystem("howdy-akka")

  val zed = system.actorOf(BarTender.props, "zed")

  val alice = system.actorOf(Person.props, "alice")

  zed.tell(Ticket, alice)

  system.awaitTermination()
}