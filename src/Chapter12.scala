object Chapter12 extends App {
  /* 1. */
  def values(fun: (Int) => Int, low: Int, high: Int): Seq[(Int, Int)] = {
    (low to high).map { x => (x, fun(x)) }
  }

  /* 2. */
  def getMax(arr: Array[Int]): Int = arr.reduceLeft(_ max _)

  /* 3. */
  def fact(n: Int): Int = (n to 1 by -1).reduceLeft(_ * _)

  /* 4. */
  def factX(n: Int): Int = (n to 1 by -1).foldLeft(1) { (prod, value) => if (value < 1) prod else prod * value }

  /* 5. */
  def largest(fun: (Int) => Int, inputs: Seq[Int]): Int = {
    val init = fun(inputs.head)
    inputs.foldLeft(init)(_ max fun(_))
  }

  /* 6. */
  def largestAt(fun: (Int) => Int, inputs: Seq[Int]): Int = {
    val head = inputs.head
    val init = (head, fun(head))
    inputs.foldLeft(init)((tuple, value) => {
      val maxValue = tuple._2 max fun(value)
      if (maxValue == tuple._2) tuple
      else (value, maxValue)
    })._1
  }

  /* 7. */
  def adjustToPair(fun: (Int, Int) => Int)(tuple: (Int, Int)): Int = fun(tuple._1, tuple._2)

  val pairs = (1 to 10).zip(11 to 20)
  val sum = adjustToPair(_ + _)(_)
  pairs.map(sum)

  /* 8. */
  val a = Array("my", "first", "function")
  val b = Array(2, 5, 8)
  a.corresponds(b)(_.length == _)

  /* 9. */
  def adjust[A, B](fun: (A, B) => Boolean)(tuple: (A, B)) = fun(tuple._1, tuple._2)

  def corresponds[A, B](a: Seq[A], b: Seq[B], p: (A, B) => Boolean) = (a zip b).forall(adjust(p))

  //def corresponds[A, B](a: Array[A], b: Array[B], p: (A, B) => Boolean) = (a zip b).forall(x => p(x._1, x._2))
  /* 10. */

  def unless[T](condition: => Boolean)(block: => T): Option[T] = {
    if (!condition) Some(block)
    else None
  }

  //def unless[T](condition: => Boolean)(block: => T): Unless[T] = {
  /*implicit def makeUnless(action: => T) = {
    new Unless[T] {
      override def `else`(b: => T): T = action
    }
  }
  if (!condition) {
    val res: T = block
    (b: => T) => res
  } else {
    (b: => T) => b
  }*/

  /*    if (!condition) {
        new Unless[T] {
          val result: T = block
          def `else`(b: => T): T = result
        }
      } else new Unless[T] {
        def `else`(b: => T): T = { result = b; result }
      }
    }

    trait Unless[T] {
      val result: T
      def `else`(block: => T): T

      override def toString: String = result.toString
    }
    val x = 0
    val res = unless(x == 0) {
      print(x);
      1
    } `else` {
        print("!");
        0
      }
    print(res)*/


}
