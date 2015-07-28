package abtechsoft

/**
 * Created by abdhesh on 7/14/15.
 */
object FactorialRecursiveApp extends App {
  val factorials = List(20, 18, 32, 28, 22, 42, 55, 48)

  val start = System.currentTimeMillis()
  for (num <- factorials) {
    println(s"factorial for $num is ${factor(num)}")
  }
  println(s"Total time taken=${System.currentTimeMillis() - start}")

  private def factor(num: Int): BigInt = {
    num match {
      case 0 => 1
      case n => n * factor(n - 1)
    }
  }
}
