package basic

import akka.actor.ActorSystem

/**
 * Created by abdhesh on 7/23/15.
 */
object HelloAkka extends App {

  import BookSeller._

  val system = ActorSystem("hello-akka")
  val bookActor = system.actorOf(BookSeller.props, name = "book-actor")
  val personActor = system.actorOf(Person.props, name = "person-actor")
  personActor.tell(Book, bookActor)
  system.awaitTermination
}
