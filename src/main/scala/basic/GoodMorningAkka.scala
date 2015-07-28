package basic

import akka.actor.{Props, ActorSystem, Actor}

/**
 * Created by abdhesh on 7/23/15.
 */
class GoodMorningAkka extends Actor {
  def receive = {
    case "goodMorning" => println("Good Morning !!")
    case _ => println("Good Night !!")
  }
}

object GoodMorningAll extends App {
  val system = ActorSystem("GoodMorningSystem")
  val goodMorningAkka = system.actorOf(Props[GoodMorningAkka], name = "goodMorningAkka")
  goodMorningAkka ! "Good Morning"
  goodMorningAkka ! "Hello, Good Morning"
  goodMorningAkka ! "goodMorning"

}
