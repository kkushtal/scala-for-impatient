import _root_.scala.collection.mutable.ArrayBuffer
import _root_.scala.util.Try

object Chapter19 extends App {

  /* 1. Реализуйте класс Bug, моделирующий жука, перемещающегося по горизонтальной линии.
   * Метод move перемещает жука в текущем направлении, метод turn изменяет направление на
   * противоположное, а метод show выводит текующую позицию. Обеспечьте возможность
   * составления цепочек из вызовов этих методов. Еапример, цепочка: */
  //bugsy.move(4).show().move(6).show().turn().move(5).show()
  class Bug {
    private var posX: Int = 0
    private var to: Int = 1

    def move(newPosX: Int): this.type = {
      posX += to * newPosX
      this
    }

    def show(): this.type = {
      println(posX)
      this
    }

    def turn(): this.type = {
      to *= -1
      this
    }
  }


  /* 2. Реализуйте "свободный" интерфейс для класса Bug из предыдущего упражнения,
   * чтобыможно былозаписать: */
  //bugsy move 4 and show and then move 6 and show turn around move 5 and show


  /* 3. Дополните свободный интерфейс, представленный в разделе 19.1 "Типы-одиночки", чтобы
   * можно было записать вызов: */
  //book set Title to "Scala for the Impatient" set Author to "Cay Horstmann"
  object Title

  object Author

  class Document {
    private var title: String = ""
    private var author: String = ""
    private var useNextArgs: Any = null

    def set(obj: Title.type): this.type = {
      useNextArgs = obj
      this
    }

    def set(obj: Author.type): this.type = {
      useNextArgs = obj
      this
    }

    def to(arg: String): this.type = {
      useNextArgs match {
        case _: Title.type => title = arg
        case _: Author.type => author = arg
        case _ => throw new Exception("Error")
      }
      this
    }
  }

  class Book extends Document

  val book = new Book


  /* 4. Реализуйте метод equals в классе Member, вложенном в класс Network, в разделе
   * 19.2 "Проекция типов". Два члена сообщества могут быть признаны равными, если
   * только они принадлежат одному сообществу. */
  class Network {
    private val members = new ArrayBuffer[Member]
    val base: Network = this

    class Member(val name: String) {
      val contacts = new ArrayBuffer[Member]

      def equals(m: Network#Member): Boolean = m match {
        case _: base.Member => true
        case _ => false
      }
    }

    def join(name: String): Member = {
      val m = new Member(name)
      members += m
      m
    }

    /*def equals(m1: Network#Member, m2: Network#Member): Boolean = Try(process(m1, m2)).isSuccess*/
    def process[M <: n.Member forSome {val n : Network}](m1: M, m2: M): (M, M) = (m1, m2)
  }


  /* 5. Взгляните на следующий псеводним типа: */
  type NetworkMember = n.Member forSome {val n: Network}

  /* и на функцию: */
  def process(m1: NetworkMember, m2: NetworkMember) = (m1, m2)

  /* Назовите отличия от функции process из раздела 19.8 "ЭКспоненциальные типы" */
  // NetworkMember - аналог Network#Member


  /* 6. В библиотеке Scala имеется тип Either, который можно использовать в реализациях
   * алгоритмов, возвращающих либо результат, либо некоторую информацию об ошибке. Еапишите
   * функцию, принимающую два параметра: отсортированный массив целых чисел и целочисленное
   * значение. Функция должна возвращать индекс значения в массиве или индекс ближайшего
   * по значению элемента. Для возвращаемого значения используйте инфиксный тип. */
  def findIndex(input: Array[Int], number: Int): String Either Int = {
    val result: String Either Int =
      try {
        val index = input.indexOf(number)
        if (index >= 0) Right(index)
        else throw new Exception("Error")
      }
      catch {
        case e: Exception => Left("Error")
      }
    result
  }


  /* 7. Реализуйте метод, принимающий экземпляр любого класса, который имеет метод:
   * def close(): Unit
   * вместе с функцией обработки этого объекта. Функция должна вызывать метод close
   * по завершении обработки или в случае какого-либо исключения. */
  def doSome(func: {def close(): Unit}): Unit = {

  }


  /* 8. Напишите функцию printValues с тремя параметрами: а, from, и to, выводящую все
   * значения f,для входных значений в заданном диапазоне от from до to. Здесь f должен быть
   * любым объектом с методом apply, получающим и возвращающим значение типа Int. Например: */
  //printValues((x: Int) => x * x, 3, 6) //Выведет 9 16 25 36
  //printValues(Array(1, 2, 3, 4, 5, 8, 13, 21, 34, 55), 3, 6) //Выведет 3 5 8 13

  def printValues(f: {def apply(x: Int): Int}, from: Int, To: Int): Unit = {
    //(from to `to`).foreach(x => s"${f(x)} ")
    //(from to To).map(x => f(x)).mkString(" ")
  }


  /* 9. Взгляните на следующий класс, моделирующий некоторое физическое измерение: */
  abstract class Dim[T](val value: Double, val name: String) {
    /* this: T => // Add */
    protected def create(v: Double): T

    def +(other: Dim[T]): T = create(value + other.value)

    override def toString: String = s"$value $name"
  }

  /* Ниже демонстрируется конкретный подкласс: */
  class Seconds(v: Double) extends Dim[Seconds](v, "S") {
    override def create(v: Double) = new Seconds(v)
  }

  /* Однако теперь, какой-нибудь неумёха сможет определить: */
  class Meters(v: Double) extends Dim[Seconds](v, "m") {
    override def create(v: Double): Seconds = new Seconds(v)
  }

  /* позволив складывать метры с секундами.
   * Попробуйте предотвратить это, определив собственный тип */


  /* 10. Обычно вместо собственных типов можно использовать трейты, наследующие классы,
   * но в некоторых ситуациях применение собственных типов изменяет порядок инициализцаии
   * и переопределения. Смоделируйте пример такой ситуации. */

}
