object Chapter06 extends App {

  /* 1. Напишите объект Conversions с методами:
   * inchesToCentimeters, gallonsToLiters, milesToKilometers */
  object Conversions {
    def inchesToCentimeters(inches: Double) = 2.54 * inches

    def gallonsToLiters(gallons: Double) = 3.78541 * gallons

    def milesToKilometers(miles: Double) = 1.60934 * miles
  }


  /* 2. Предыдущую задачу трудно назвать объектно-ориентированной.
   * Реализуйте общий суперкласс UnitConversion и определите объекты:
   * InchesToCentimeters, GallonsToLiters и MilesToKilometers, наследующие его */
  class UnitConversion {
    def apply(value: Double, factor: Double) = factor * value
  }

  object InchesToCentimeters extends UnitConversion {
    def apply(inches: Double) = super.apply(inches, 2.54)
  }

  object GallonsToLiters extends UnitConversion {
    def apply(gallons: Double) = super.apply(gallons, 3.785)
  }

  object MilesToKilometers extends UnitConversion {
    def apply(miles: Double) = super.apply(miles, 1.609)
  }


  /* 3. Определите объект Origin, наследующий класс java.awt.Point.
   * Почему это не самая лучшая идея? (Рассмотрите поближе методы класса Point.) */
  object Origin extends java.awt.Point


  /* 4. Определите класс Point с объектом-компаньоном, чтобы можно было
   * конструировать экземпляры Point, как Point(3, 4), без ключевого слова new. */
  class Point(var x: Int, var y: Int)

  object Point {
    def apply(x: Int, y: Int) = new Point(x, y)
  }


  /* 5. Напишите приложение на языке Scala, используя трейт (trait) App,
   * которое выводит аргументы командной строки в обратном порядке, раз\\азделяя их пробелами.
   * Например, команда scala Reverse Hello Word должна вывести World Hello */
  object Reverse extends App {
    println(args.reverse.mkString(" "))
  }


  /* 6. Напишите перечисление, описывающее четыре масти игральных карт так,
     * чтобы метод toString возвращал "♣", "♦", "♥" или "♠" */
  object Suit extends Enumeration {
    type Suit = Value

    val Clubs = Value('\u2663'.toString)
    val Diamonds = Value('\u2666'.toString)
    val Hearts = Value('\u2764'.toString)
    val Spades = Value('\u2660'.toString)
  }


  /* 7. Реализуйте функцию для проверки масти карты, реализованной в предыдущем упражнении,
     * которая проверяла бы принадлежность к красной масти */

  import Suit._

  def isRed(suit: Suit) = suit == Hearts || suit == Diamonds


  /* 8. Напишите перечисление, описывающее восемь углов куба RGB. В качестве числовых идентификаторов
     * должны использоваться значения цвета (например, 0xff0000 - для Red) */
  object RGB extends Enumeration {
    type RGB = Value

    val Black = Value(0x000000)
    val Red = Value(0xff0000)
    val Green = Value(0x00ff00)
    val RedGreen = Value(0xffff00)
    val Blue = Value(0x0000ff)
    val RedBlue = Value(0xff00ff)
    val GreenBlue = Value(0x00ffff)
    val White = Value(0xffffff)
  }

}
