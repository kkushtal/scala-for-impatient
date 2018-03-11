object Chapter11 extends App {

  /* 1. */
  class Number(val value: Int) {
    def apply(): Number = Number(3) + Number(4) -> Number(5)

    def +(that: Number): Number = operate(that, _ + _)

    def ->(that: Number): Number = operate(that, _ * _)

    def operate(other: Number, func: (Int, Int) => Int) = Number(func(value, other.value))

    override def toString: String = f"value: $value"
  }

  object Number {
    def apply(value: Int): Number = new Number(value)
  }


  /* 3 */
  class Fraction(n: Int, d: Int) {
    private val num: Int = n
    private val den: Int = d

    override def toString: String = f"$this.num/$this.den"

    def sign(a: Int): Int = a match {
      case x if x > 0 => 1
      case x if x < 0 => -1
      case _ => 0
    }

    def gcd(a: Int, b: Int): Int = b match {
      case 0 => math.abs(a)
      case _ => gcd(b, a % b)
    }

    def -(other: Fraction): Fraction = operate(other, _ - _)

    def +(other: Fraction): Fraction = operate(other, _ + _)

    def *(other: Fraction): Fraction = Fraction(num * other.num, den * other.den)

    def /(other: Fraction): Fraction = Fraction(num * other.den, den * other.num)

    def operate(other: Fraction, func: (Int, Int) => Int): Fraction = {
      val num = func(this.num * other.den, this.den * other.num)
      val den = this.den * other.den
      Fraction(num, den)
    }

    /*override def equals(other: Any): Boolean = other match {
      case other: Fraction => this.den == other.den && this.num == other.num
      case _ => false
    }

    override def hashCode: Int = {
      val prime = 10
      var result = 1
      result = prime * result + num
      result = prime * result + den
      result
    }
*/
  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }


  /* 4 */
  class Money(d: Int, c: Int) {
    val dollars: Int = d
    val cents: Int = c

    def +(other: Money): Money = operate(other, _ + _)

    def *(other: Money): Money = operate(other, _ * _)

    def <(other: Money): Boolean = compare(other, _ < _)

    def >(other: Money): Boolean = compare(other, _ > _)

    private def amount: Int = dollars * 100 + cents

    private def compare(other: Any, func: (Int, Int) => Boolean): Boolean = other match {
      case other: Money => func(amount, other.amount)
      case _ => false
    }

    override def equals(other: Any): Boolean = other match {
      case other: Money => dollars == other.dollars && cents == other.cents
      case _ => false
    }

    /*override def hashCode(): Int = {
      val prime = 57
      var result = 1
      result = prime * result + dollars
      result = prime * result + cents
      result
    }*/

    def operate(other: Money, func: (Int, Int) => Int): Money = {
      var inCents = func(cents, other.cents)
      var (outDollars, outCents) = BigInt(inCents) /% 100
      outDollars += func(dollars, other.dollars)
      Money(outDollars.intValue, outCents.intValue)
    }

    override def toString: String = f"amount: $dollars.$cents $$"
  }

  object Money {
    def apply(d: Int, c: Int): Money = {
      new Money(d, c)
    }
  }


  /* 5 */
  class Table() {

    import _root_.scala.collection.mutable.ArrayBuffer

    private val rows: ArrayBuffer[ArrayBuffer[String]] = ArrayBuffer(ArrayBuffer.empty[String])

    def apply(): Table = this | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM, .NET"

    def |(value: String): Table = {
      rows.last += value
      this
    }

    def ||(value: String): Table = {
      rows += ArrayBuffer[String](value)
      this
    }

    override def toString: String = rows.map(_.mkString("<tr><td>", "</td><td>", "</td></tr>")).mkString("<table>", "", "</table>")
  }

  object Table {
    def apply(): Table = new Table()
  }

  /* 6 */
  class ASCIIArt(pic: String) {
    private val lines = pic.split("\n")

    def +(other: ASCIIArt): ASCIIArt = {
      val pics = lines.zip(other.lines)
      val out = pics.map { case (x, y) => f"$x $y" }
      new ASCIIArt(out.mkString("\n"))
    }

    override def toString: String = pic
  }

  object ASCIIArt {
    def apply(pic: String): ASCIIArt = new ASCIIArt(pic)
  }

  val x = ASCIIArt(
    """/\_/\
( ' ' )
(  -  )
 | | |
(__|__)""")

  val y = ASCIIArt(
    """   -----
 / Hello \
<  Scala |
 \ Coder /
   -----""")

  /* 7 */
  class BitSequence {

  }


  /* 8 */
  /*class Matrix(n: Int) {
    val matrix: Array[Array[Int]] = Array.ofDim[Int](n, n)
    def update(m: Int, n: Int, value: Int): Unit = matrix(m)(n) = value

  }
  object Matrix {
    def apply(n: Int): Matrix = new Matrix(n)
  }*/

  /* 9 */
  object RichFile {
    def unapply(fullPath: String): Option[(String, String, String)] = {
      val regex = """(.*)/(.*)\.(.*)""".r
      fullPath match {
        case regex(path, name, ext) => Some(path, name, ext)
        case _ => None
      }
    }
  }

  /* 10 */
  class RichFileX {
    def unapplySeq(value: String): Option[Seq[String]] = {
      val regex = """([^/]+)/?""".r
      val matches = regex.findAllIn(value)
      val seq = matches.map(part => {
        val regex(value) = part;
        value
      })
      if (seq.nonEmpty) Some(seq.toSeq) else None
    }
  }

}
