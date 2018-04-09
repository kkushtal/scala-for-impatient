import _root_.scala.annotation._
import _root_.java.awt.Point

object Chapter21 extends App {

  /* 1. Как действует оператор ->? То есть как заставить выражения "Hello" -> 42 и 42 -> "Hello"
   * образовывать пары ("Hello", 42) и (42, "Hello")? Подсказка: Predef.any2ArrowAssoc */
  implicit class ArrowAssoc[T, R](val key: T) {
    def ->(value: R): (T, R) = (key, value)
  }

  def checkArrowAssoc(): Unit = {
    assert("Hello" -> 2 == ("Hello", 2))
    assert(42 -> "Hello" == (2, "Hello"))
  }


  /* 2. Определите оператор +%, добавляющий указанный процент к значению. Например, выражение
   * 120 +% 10 должно вернуть 132. Используйте неявный класс. */
  implicit class PercentInt(val from: Int) {
    def +%(percent: Int): Int = {
      val factor: Int = (100 + percent) / 100
      from * factor
    }
  }


  /* 3. Определите оператор ! вычисления факториала, чтобы выражение 5.! возвращало 120.
   * Используйте неявный класс. */
  implicit class FactorialInt(val from: Int) {
    def !(): Int = {
      @tailrec def fact(n: Int, acc: Int = 1): Int = {
        if (n == 1) acc
        else fact(n - 1, acc * n)
      }

      fact(from)
    }
  }

  /* 4. Некоторые программисты обожают "свободные API", которые читаются почти как обычный
   * текст на английском языке. Определите такой API для чтения в консоли целых и вещественных
   * чисел, а также строк, чтобы с его помощью можно было записать, например такую инструкцию:
   * Obtain aString asking For "Your name" and anInt askingFor "Your age" and aDouble askingFor "Your weight". */


  /* 5. Реализуйте всё необходимое для вычисления выражения: */
  //smaller(Fraction(1, 7), Fraction(2, 9))
  /* с классом Fraction из главы 11. Добавьте неявный класс RichFraction, наследующий Ordered[Fraction].*/
  class Fraction(val num: Int, val den: Int) {
    def -(that: Fraction): Fraction = {
      val _num = (num * that.den) - (den * that.num)
      val _den = den * that.den
      Fraction(_num, _den)
    }
  }

  object Fraction {
    def apply(num: Int, den: Int): Fraction = new Fraction(num, den)
  }

  implicit class RichFraction(val self: Fraction) extends Ordered[Fraction] {
    override def compare(that: Fraction): Int = (self - that).num
  }

  def smaller[T](a: T, b: T)(implicit order: T => Ordered[T]): T = {
    if (a < b) a else b
  }

  /* 6. Реализуйте лексикографическое сравнение объектов класса java.awt.Point. */
  object LexicographicPoint {

    implicit class RichPoint(self: Point) extends Ordered[Point] {
      override def compare(that: Point): Int = that match {
        case p if self.x == p.x => self.y - p.x
        case p => self.x - p.x
      }
    }

  }

  /* 7. Продолжите предыдущее упражнение и реализуйте сравнение двух точек на координатной
   * плоскости по их расстояниям от центра координат. Как можно обеспечить выбор между двумя сравнениями? */
  object DistancePoint {

    implicit class RichPoint(self: Point) extends Ordered[Point] {
      override def compare(that: Point): Int = {
        val z = new Point(0, 0)
        (self.distance(z): Double, that.distance(z): Double) match {
          case (d1, d2) if d1 < d2 => -1
          case (d1, d2) if d2 > d2 => 1
          case _ => 0
        }
      }
    }

  }

  /* 8. Воспользуйтесь командой implicity в интерактивной оболочке REPL, чтобы получить список
   * неявных объектов, описанных в разделе 21.5 "Неявные параметры" и в разделе 21.6 "Неявные
   * преобразования с неявными параметрами". Какие объекты вы получили? */
  case class Delimiters(left: String, right: String)

  def quote(what: String)(implicit delims: Delimiters): String = {
    delims.left + what + delims.right
  }

  object FrenchPunctuation {
    implicit val quoteDelimiters: Delimiters = Delimiters("\"", "\"")
  }

  import FrenchPunctuation._

  /* 1 implicit members imported from FrenchPunctuation */
  /* 1 defined in FrenchPunctuation */
  /* implicit val quoteDelimiters: Delimiters */


  /* 9. Объясните почему Ordering является классом типов, а Ordered - нет.*/


  /* 10. Обобщите метод average из раздела 21.8 "Классы типов" до Seq[T] */
  trait NumberLike[T] {
    def plus(x: T, y: T): T

    def divBy(x: T, n: Int): T
  }

  object NumberLike {

    implicit object NumberLikeDouble extends NumberLike[Double] {
      def plus(x: Double, y: Double): Double = x + y

      def divBy(x: Double, n: Int): Double = x / n
    }

  }

  def average[T: NumberLike](seq: T*): T = {
    val ev = implicitly[NumberLike[T]]
    val sum = seq.reduceLeft(ev.plus) //seq.sum
    ev.divBy(sum, seq.length)
  }

  /* 11. Сделайте тип String членом класса типов NumberLike из раздела 21.8 "Классы типов"
   * Метод divBy должен сохранять каждую n-ю букву, то есть вызов average("Hello", "World")
   * должен вернуть "Hlool"*/
  implicit object NumberLikeString extends NumberLike[String] {
    def plus(x: String, y: String): String = x + y

    def divBy(x: String, n: Int): String = x.zipWithIndex.view.filter(_._2 % n == 0).map(_._1).force.mkString
  }


}
