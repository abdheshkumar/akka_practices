package abtechsoft

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by abdhesh on 8/15/15.
 */
object FactorialTailRecursiveFutureApp extends App {
  val factorials = List(20, 18, 32, 28, 22, 42, 55, 48)
  val start = System.currentTimeMillis()

  val futuresResult = Future.sequence(factorials.map(factor))
  futuresResult onSuccess {
    case results =>
      results.foreach(f => println(s"factorial for ${f._1} is ${f._2}"))
      println(s"Total time taken=${System.currentTimeMillis() - start}")
  }


  private def factor(num: Int) = Future(num -> factorTail(num, 1))

  @tailrec private def factorTail(num: Int, acc: BigInt): BigInt = {
    (num, acc) match {
      case (0, a) => a
      case (n, a) => factorTail(n - 1, n * a)
    }
  }
}
