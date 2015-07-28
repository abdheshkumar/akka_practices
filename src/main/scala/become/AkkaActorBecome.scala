package become

import akka.actor.{Props, ActorSystem, Actor}

/**
 * Created by abdhesh on 7/25/15.
 */


object Born

case class Evolve(numberOfNeighbors: Int)

object Status

// receive is the method that handles incoming messages.
// alive: Receive and dead: Receive are defining alternative ways to respond to messages
// these will be switched on during runtime
class Cell extends Actor {

  def alive: Receive = {
    case Evolve(neighbors) => {
      if (neighbors < 2) context.become(dead)
      else if (neighbors > 3) context.become(dead)
    }
    case Status => println("alive!")
  }

  def dead: Receive = {
    case Evolve(neighbors) => {
      if (neighbors == 3) context.become(alive)
    }
    case Status => println("dead :(")
  }

  def receive = {
    case Born => context.become(alive)
    case Status => println("I am nothing...")
  }
}

object AkkaActorBecome extends App {
  val system = ActorSystem("akkasystem")

  val cell = system.actorOf(Props[Cell], "cell")
  // returns reference, very few public methods (id, start, stop, etc)

  // ! = send message ... so we are sending Status message to cell
  cell ! Status // > I am nothing ...
  cell ! Born
  cell ! Status // > alive!

  cell ! Evolve(4)
  cell ! Status // > dead :(

  cell ! Evolve(3)
  cell ! Status // alive!

  // async calls so being lazy and just adding thread sleep ~
  Thread.sleep(250)

  system.shutdown()
}

// the whole born thing kinda sucks as its an undefined state (neither alive nor dead).
// you'd want a factory to create alive or dead cells, although I think in most solutions
// you don't want a cell class at all :)

