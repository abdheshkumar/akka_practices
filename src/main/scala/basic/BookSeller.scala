package basic

import akka.actor.{Props, ActorLogging, Actor}

/**
 * Created by abdhesh on 7/23/15.
 */
class BookSeller extends Actor with ActorLogging {

  import BookSeller._


  def receive = {
    case Title => log.info("Trying to find your book")
    case WrongTitle => log.info("No such a book")
  }
}

object BookSeller {
  case object Title
  case object Book
  case object WrongTitle

  def props = Props[BookSeller]
}

