package practices

import akka.actor.{ActorRef, Actor, ActorLogging}

/**
 * Created by abdhesh on 7/24/15.
 */
trait ProducerBehavior {
  this: Actor =>

  val producerBehavior: Receive = {
    case GiveMeThings =>
      sender() ! Give("thing")
  }
}

trait ConsumerBehavior {
  this: Actor with ActorLogging =>

  val consumerBehavior: Receive = {
    case ref: ActorRef => ref ! GiveMeThings

    case Give(thing) => log.info("Got a thing! It's {}", thing)
  }
}

class Producer extends Actor with ProducerBehavior {
  def receive = producerBehavior
}

class Consumer extends Actor with ActorLogging with ConsumerBehavior {
  def receive = consumerBehavior
}

class ProducerConsumer extends Actor with ActorLogging
with ProducerBehavior with ConsumerBehavior {

  def receive = producerBehavior.orElse(consumerBehavior)
}

// protocol
case object GiveMeThings

final case class Give(thing: Any)