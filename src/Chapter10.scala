import _root_.java.awt.geom.Ellipse2D
import _root_.java.awt.Point
import _root_.scala.math.Ordered
import _root_.java.beans.{PropertyChangeEvent, PropertyChangeListener, PropertyChangeSupport}
import _root_.java.io.{BufferedInputStream, FileInputStream, InputStream}


object Chapter10 extends App {
  /* 1. */
  trait RectangleLike {
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

  object EllipseRectangleLike {
    def apply(x: Double, y: Double, w: Double, h: Double) = {
      new Ellipse2D.Double(x, y, w, h) with RectangleLike
    }
  }

  /* 2. */
  class OrderedPoint(x: Int, y: Int) extends Point with Ordered[Point] {
    def compare(that: Point): Int = that match {
      case point if x < point.x => -1
      case point if x == point.x && y < point.y => -1
      case point if x == point.x && y == point.y => 0
      case _ => 1
    }
  }

  /* 3. */

  /* 4. */
  trait Logger {
    def log(msg: String): Unit
  }

  trait ConsoleLogger extends Logger {
    override def log(msg: String): Unit = print(msg)
  }

  trait CaesarLogger extends Logger {
    val key: Int = 3

    abstract override def log(msg: String): Unit = super.log(msg.map(char => (char + key).toChar))
  }

  class CryptoLogger extends Logger {
    def log(msg: String): Unit = {}
  }

  object CryptoLogger {
    def apply(msg: String): Unit = {
      val caesar = new {
        override val key = -3
      } with CryptoLogger with ConsoleLogger with CaesarLogger
      caesar.log(msg)
    }
  }

  /* 5. */
  trait PropertyChangeSupportLike {
    private val support = new PropertyChangeSupport(this)

    def addPropertyChangeListener(propertyName: String, listener: PropertyChangeListener) = {
      support.addPropertyChangeListener(propertyName, listener)
    }

    def addPropertyChangeListener(listener: PropertyChangeListener) = {
      support.addPropertyChangeListener(listener)
    }

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Int, newValue: Int) = {
      support.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)
    }

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Boolean, newValue: Boolean) = {
      support.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)
    }

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Any, newValue: Any) = {
      support.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)
    }

    def firePropertyChange(event: PropertyChangeEvent): Unit = {
      support.firePropertyChange(event)
    }

    def firePropertyChange(propertyName: String, oldValue: Int, newValue: Int): Unit = {
      support.firePropertyChange(propertyName, oldValue, newValue)
    }

    def firePropertyChange(propertyName: String, oldValue: Boolean, newValue: Boolean): Unit = {
      support.firePropertyChange(propertyName, oldValue, newValue)
    }

    def firePropertyChange(propertyName: String, oldValue: Any, newValue: Any): Unit = {
      support.firePropertyChange(propertyName, oldValue, newValue)
    }

    def getPropertyChangeListeners(): Array[PropertyChangeListener] = {
      support.getPropertyChangeListeners()
    }

    def getPropertyChangeListeners(propertyName: String): Array[PropertyChangeListener] = {
      support.getPropertyChangeListeners(propertyName)
    }

    def hasListeners(propertyName: String): Boolean = {
      support.hasListeners(propertyName)
    }

    def removePropertyChangeListener(listener: PropertyChangeListener): Unit = {
      support.removePropertyChangeListener(listener)
    }

    def removePropertyChangeListener(propertyName: String, listener: PropertyChangeListener): Unit = {
      support.removePropertyChangeListener(propertyName, listener)
    }
  }

  object PropertyChangeSupportLike {
    def apply(x: Int, y: Int) = new Point(x, y) with PropertyChangeSupportLike
  }

  /* 6. */

  /* 7. */

  /* 8. */
  trait BufferedInputStreamLike extends InputStream {
    private val inStream = new BufferedInputStream(this)

    override def read(): Int = inStream.read()
  }

  object InputStream {
    def apply(filepath: String) = {
      new FileInputStream(filepath) with BufferedInputStreamLike
    }
  }

  /* 9. */
  trait BufferedInputStreamLikeX extends InputStream {
    private val inStream = new BufferedInputStream(this) with Logger {
      override def read(): Int = {
        log(count.toString)
        super.read()
      }

      override def log(msg: String): Unit = {}
    }

    override def read(): Int = inStream.read()
  }

  object InputStreamX {
    def apply(filename: String) = {
      new FileInputStream(filename) with BufferedInputStreamLikeX with ConsoleLogger
    }
  }


  /* 10. */
  class IterableInputStream(in: InputStream) extends InputStream with Iterable[Byte] {
    def read(): Int = in.read()

    def iterator(): Iterator[Byte] = new Iterator[Byte] {
      def hasNext(): Boolean = in.available() > 0

      def next(): Byte = in.read.toByte
    }
  }

  object IterableInputStream {
    def apply(filepath: String): Unit = {
      val inStream = new IterableInputStream(new FileInputStream(filepath))
      for (byte <- inStream) print(byte.toChar)
      inStream.close()
    }
  }

}
