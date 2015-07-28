package become

import akka.actor.{Actor, ActorSystem, Props}

/**
 * Created by abdhesh on 7/25/15.
 */
class HotSwapActor extends Actor {

  import context._

  def angry: PartialFunction[Any, Unit] = {
    case "foo" => sender ! "I am already angry!"
    case "bar" => become(happy)
  }

  def happy: PartialFunction[Any, Unit] = {
    case "bar" => sender ! "I am already happy :-)"
      unbecome
    case "foo" => become(angry)
  }

  def receive = {
    case "foo" => become(angry)
    case "bar" => become(happy)
  }
}

class OtherActor extends Actor {
  val actor = context.actorOf(Props[HotSwapActor])

  def receive = {
    case "start" =>
      actor ! "foo"
      actor ! "bar"
      actor ! "bar"
      actor ! "foo"
    case a@_ => println(a)
  }
}

/*
Msg "foo" sent --> receive receives the message. angry becomes the receive function. Any next message will be sent to angry
Msg "bar" sent --> angry receives message. happy becomes the receive function. Any next message will be sent to happy
Msg "bar" sent --> happy receives message. It replied I am already happy :-) message. And then it unbecomes. As per api for all the previous calls to context.become, the discardOld was set as default to true. Now after replacing itself there is nothing left to become the next receiver. It takes the default one i.e. receive as the receiver
Msg "foo" sent --> receive receives the message. angry becomes the receive function. Any next message will be sent to angry
 */

object HotSwapMain extends App {
  val system = ActorSystem("actorsystem")
  val actor = system.actorOf(Props[OtherActor])
  actor ! "start"
}