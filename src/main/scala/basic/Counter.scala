package basic

import akka.actor.{Props, Actor}

/**
 * Created by abdhesh on 7/23/15.
 */
class Counter extends Actor {
  def counter(n: Int): Receive = {
    case "incr" => context become (counter(n + 1))
    case "get" =>
  }

  def receive = counter(0)
}

object Counter {
  def props = Props[Counter]
}