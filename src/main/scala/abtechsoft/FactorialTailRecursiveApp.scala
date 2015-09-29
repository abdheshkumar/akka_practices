package abtechsoft

import scala.annotation.tailrec

/**
 * Created by abdhesh on 7/14/15.
 */
object FactorialTailRecursiveApp extends App {
  val factorials = List(20, 18, 32, 28, 22, 42, 55, 48)
  val start = System.currentTimeMillis()
  for (num <- factorials) {
    println(s"factorial for $num is ${factor(num)}")
  }
  println(s"Total time taken=${System.currentTimeMillis() - start}")

  private def factor(num: Int) = factorTail(num, 1)

  @tailrec private def factorTail(num: Int, acc: BigInt): BigInt = {
    (num, acc) match {
      case (0, a) => a
      case (n, a) => factorTail(n - 1, n * a)
    }
  }
}
