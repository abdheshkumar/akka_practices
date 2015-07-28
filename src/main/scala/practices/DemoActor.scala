package practices

import akka.actor.{Actor, Props}

/**
 * Created by abdhesh on 7/24/15.
 */
object DemoActor {
  /**
   * Create Props for an actor of this type.
   * @param magicNumber The magic number to be passed to this actor’s constructor.
   * @return a Props for creating this actor, which can then be further configured
   *         (e.g. calling `.withDispatcher()` on it)
   */
  def props(magicNumber: Int): Props = Props(new DemoActor(magicNumber))
}

class DemoActor(magicNumber: Int) extends Actor {
  def receive = {
    case x: Int => sender() ! (x + magicNumber)
  }
}

class SomeOtherActor extends Actor {
  // Props(new DemoActor(42)) would not be safe
  val demoActor = context.actorOf(DemoActor.props(42), "demo")

  def receive = {
    case "DemoActor" =>
  }
}