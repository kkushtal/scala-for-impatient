object Chapter08 extends App {

  /* 1. Определите класс CheckingAccount, наследующий класс BankAccount,
   * который взымает $1 комиссионных за каждую операциюп пополнения или списания. */
  class BankAccount(initialBalance: Double) {
    private var balance = initialBalance

    def deposit(amount: Double) = {
      balance += amount
      balance
    }

    def withdraw(amount: Double) = {
      balance -= amount
      balance
    }
  }

  class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    private val comission: Int = 1

    override def deposit(amount: Double): Double = super.deposit(amount - comission)

    override def withdraw(amount: Double): Double = super.withdraw(amount + comission)
  }


  /* 2. Определите класс SacingAccount, наследующий класс BankAccount из предыдущего упражнения,
   * который начисляет проценты каждый месяц (вызовом метода earnMothlyInterest)
   * и позволяет бесплатно выполнять три операции зачисления или списания каждый месяц.
   * Метод earnMothlyInterest должен сбрасывать счётчик транзакций. */
  class SavingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    private val comissionDefault: Double = 1d
    private val mothlyInterest: Double = 0.01
    private var balance: Double = initialBalance
    private val transaction: Transaction = new Transaction(3)

    def earnMothlyInterest(): Unit = {
      transaction.refresh()
      balance = super.deposit(balance * mothlyInterest)
    }

    private def comission: Double = if (transaction.haveFree) 0d else comissionDefault

    override def deposit(amount: Double): Double = {
      balance = super.deposit(amount - comission)
      balance
    }

    override def withdraw(amount: Double): Double = {
      balance = super.withdraw(amount + comission)
      balance
    }

    class Transaction(initialFreeCount: Int) {
      private var freeCount: Int = initialFreeCount

      def refresh() = freeCount = initialFreeCount

      def haveFree: Boolean =
        if (freeCount > 0) {
          freeCount -= 1
          true
        } else false
    }

  }

  /* 3. Обратитесь к какой-нибудь книге по языку Java или C++,
   * где приводится пример простой иерархии классов, возможно,
   * вовлекающей работников, животных, геометрические фигуры или нечно подобное.
   * Реализуйте этот пример на языке Scala. */

  /* 4. Определите абстрактный класс элемента Item с методами price и description.
   * Определите подкласс простого элемента SimpleItem, представляющий элемент,
   * цена и описание которого определяются в конструкторе. Используйте тот факт, что
   * объявление val может переопределять def. Определите класс Bundle - пакет элементов,
   * содержащий другие элементы. Его цена должна определяться как сумма цен элементов в пакете.
   * Реализуйте такэе механизм добавления элементов в пакет и соответствующий метод description. */
  abstract class Item {
    def price: Double

    def description: String
  }

  class SimpleItem(val price: Double, val description: String) extends Item

  class Bundle(private var items: List[Item] = List.empty[Item]) {
    def addItem(item: Item): List[Item] = {
      items :+= item
      items
    }

    def price: Double = items.foldLeft(0d)(_ + _.price)

    def description: List[String] = items.map(_.description)
  }


  /* 5. Спроектируйте класс точки Point, значения координат x и y которой
   * передаются конструктору. Реализуйте подкласс точки и подписью LabeledPoint,
   * конструктор которого будет принимать строку с подписью и значения координат x и y. например:
   * new LabeledPoint("Black Thursday", 1929, 230.07) */
  class Point(val x: Double, val y: Double)

  class LabeledPoint(var label: String, override val x: Double, override val y: Double) extends Point(x, y)


  /* 6. Определите абстрактный класс геометрической фигуры Shape с абстрактным методом
   * centerPoint и подскалыы прямоугольника и окружности, Rectangle и Circle. Реализуйте
   * соответствующие конструкторы в подклассах и переопределите метод centerPoint в каждом классе. */
  abstract class Shape {
    def centerPoint: Point
  }

  class Rectangle(point1: Point, point2: Point) extends Shape {
    def centerPoint: Point = new Point((point1.x + point2.x) / 2, (point1.y + point2.y) / 2)

  }

  class Circle(override val centerPoint: Point, radius: Double) extends Shape


  /* 7. Определите класс Square, наследующий класс java.awt.Rectangle и имеющий три конструктора:
   * один, создающий квадрат по указанным координатам угла и ширине,
   * другой, создающий квадрат с углом в точке (0, 0) с указанной шириной,
   * и третий, создающий квадрат с углом в точке (0, 0) с шириной 0. */
  class PointX(val x: Int, val y: Int)

  class Square(point: PointX, width: Int = 0) extends java.awt.Rectangle(point.x, point.y, width, width) {
    def this(width: Int) = this(new PointX(0, 0), width)

    def this() = this(0)
  }

  class SquareX(point: PointX = new PointX(0, 0), width: Int = 0) extends java.awt.Rectangle(point.x, point.y, width, width) {
    def this(width: Int) = this(new PointX(0, 0), width)
  }


  /* 8. Скомпилируйте классы Person и SecretAgent из раздела 8.6 "Переопределение полей"
   * и проанализируйте результаты компиляции с помощью javap. Сколько полей name вы обнаружили?
   * Сколько методов чтения name вы обнаружили? Что они делают? (Подсказка: используйте ключи -c и -private) */
  class Person(val name: String) {
    override def toString: String = getClass.getName + "[name=" + name + "]"
  }

  class SecretAgent(codename: String) extends Person(codename) {
    override val name = "secret"
    override val toString = "secret"
  }


  /* 9. В классе Creature из раздела 8.10 "Порядок создания и пережающие определения"
   * замените val range на def. Что ипроизойдёт, когда вы будете использовать def в классе Ant?
   * Что произойдёт, если в в подклассе использовать val? Почему? */
  class Creature {
    def range: Int = 10

    val env: Array[Int] = new Array[Int](range)
  }

  class Ant extends Creature {
    override def range = 2
  }


  /* 10. Файл scala/collection/immutable/Stack.scala содержит определеие класса
   * Stack[A] protected (protected val elems: List[A])
   * Объясните назначение ключевых слов protected. */

  import scala.collection.immutable.Stack

  class Example(override val elems: List[Nothing]) extends Stack

  /* //Errors
   * val stack = new Stack(List.empty[Nothing])
   * val lst = Stack.elems */
}
