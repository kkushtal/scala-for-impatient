import java.awt.geom.Ellipse2D
import _root_.java.awt.Point


object Chapter10 extends App {

  /*trait RectangleLike extends java.awt.geom.RectangularShape {

    def getX(): Double

    def getY(): Double

    def getWidth(): Double

    def getHeight(): Double

    def setFrame(x: Double, y: Double, width: Double, height: Double)

    def translate(dx: Int, dy: Int) {
      setFrame(getX + dx, getY + dy, getWidth, getHeight)
    }

    def grow(h: Int, v: Int) {
      setFrame(getX - h, getY - v, getWidth + 2 * h, getHeight + 2 * v)
    }
  }

  val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
  egg.translate(10, -10)
  egg.grow(10, 20)
  println(egg.getX)
  println(egg.getY)
  println(egg.getWidth)
  println(egg.getHeight)*/


  /* 2. */
  class orderedPoint(x: Int, y: Int) extends Point with scala.math.Ordered[Point] {
    override def compare(that: Point): Int = that match {
      case p if x < p.x => -1
      case p if x == p.x && y < p.y => -1
      case p if x == p.x && y == y => 0
      case _ => 1
    }
  }


  /* 3. */


  /* 4. */
  /*trait Logger {
    def log(msg: String)
  }

  trait CryptoLogger extends Logger {
    var key: Int = 3

    def log(msg: String) = super.log(msg + "!")
  }

  class Log extends Logger {
    def log(msg: String) = println(msg)
  }

  val logger = new Log with CryptoLogger {
    override val key = -3
  }*/

}
