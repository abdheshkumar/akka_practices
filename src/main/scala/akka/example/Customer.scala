package akka.example

import akka.actor.{ActorRef, Actor}

/**
 * Created by abdhesh on 8/3/15.
 */
case object CaffeineWithdrawalWarning

class Customer(caffeineSource: ActorRef) extends Actor {
  def receive = {
    case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
    case Bill(cents) => println(s"I have to pay $cents cents, or else!")
  }
}