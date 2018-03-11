object Chapter04 {

  def main(args: Array[String]) {
//    ex07
    val person = new Person("PERSON")
  }

  class Person(val name: String) { outer =>
    class Woman(val name: String) {
      override def toString(): String = this.name + " " + outer.name
    }
    val woman = new Woman("WOMAN")
    println(woman)
  }



  object obj {
    def until(condition: => Boolean)(block: => Unit) {
      if (!condition) {
        block
        until(condition)(block)
      }
    }
    var x = 10
    until(x == 0) {
      println(x)
      x -= 1
    }
  }

  //println(ex.countWordsMutableMap)
  //https://github.com/viktor-podzigun/scala-impatient/blob/master/src/main/scala/Chapter04.scala
  object ex {

    import java.util.Scanner
    import java.io.File

    import scala.collection.mutable

    def countWordsMutableMap(): mutable.Map[String, Int] = {
      val words = new mutable.HashMap[String, Int]

      processWords(w => words(w) = words.getOrElse(w, 0) + 1)
      words
    }

    private def processWords(process: String => Unit): Unit = {

      val file = new File("C:/scala/example.txt")
      val in = new Scanner(file)
      try {
        while (in.hasNext) {
          val v = in.next()
          process(v)
        }
      } finally {
        in.close()
      }
    }
  }


  /*1. Создайте ассоциативный массив с ценами на вещи, которые вы хотели бы приобрести.
  * Затем создайте второй ассоциативный массив с теми же ключами и ценами с 10%-ной скидкаой.*/
  val things = Map("shirt" -> 31.10, "jacket" -> 59.50, "pants" -> 65.40)

  def ex01(things: Map[String, Double]) = things.mapValues(_ * .9)

  object Excercise02 {
    val question = "Напишите программу, читающую слова из файла. " +
      "Используйте изменяемый ассоциативный массив для подсчёта вхождений каждого слова. " +
      "Для чтения используйте java.util.Scanner." +
      "В конце выведите все слова и их счётчики."

    def answer(path: String = "C:/scala/example.txt") = {
      val file = new java.io.File(path)
      val in = new java.util.Scanner(file)
      val words = scala.collection.mutable.Map.empty[String, Int]

      while (in.hasNext()) {
        val key = in.next()
        words(key) = words.getOrElse(key, 0) + 1
      }
      words.foreach { case (k, v) => println(f"$k%10s $v%10d") }
    }

    answer()
  }

  object Excercise03 {
    val question = "Выполните предыдущее упражнение, используя неизменяемый ассоциативный массив."
  }

  object Excercise04 {
    val question = "Выполните предыдущее упражнение, используя сортированный ассоциативный массив, чтобы слова выводились в отсоритрованном порядке."
  }

  object Excercise05 {
    val question = "Выполните предыдущее упражнение, используя java.util.Tree-Map, адаптировав его для работы со Scala API."
  }

  /* 6. Определите связанную хеш-таблицу, отображающую 'Monday' в java.util.Calender.MONDAY,
  * и так далее для других дней недели. Продемонстрируйте обход элементов в порядке их добавления.*/
  def ex06() = {
    import java.util.Calendar._
    val weekDays = scala.collection.mutable.LinkedHashMap.empty[String, Int]

    weekDays += ("Monday" -> MONDAY,
      "Thursday" -> THURSDAY,
      "Wednesday" -> WEDNESDAY,
      "Thursday" -> THURSDAY,
      "Friday" -> FRIDAY,
      "saturday" -> SATURDAY,
      "Sunday" -> SUNDAY)
    weekDays.foreach{case(k, v) => println(f"$k%10s = $v")}
  }

  /* 7. Выведите таблицу всех Java-свойств.
  * Для этого перед выводом таблицы нужно отыскать длину самого длинного ключа.*/
  def ex07 = {
    import scala.collection.JavaConversions.propertiesAsScalaMap
    import scala.collection.Map

    val props: Map[String, String] = System.getProperties()
    val max = props.keys.maxBy(_.length).length
    props.foreach { case (k, v) => printf("%-" + max + "s | %s\n", k, v) }
  }

  /* 8. Напишите функцию minmax(values: Array[Int]), возвращающую пару,
  * содержащую наименьшее и наибольшее значения */
  def minmax(values: Array[Int]) = (values.min, values.max)

  /* 9. Напишите функцию lteqqt(values: Array[Int], v: Int]), возвращающую тройку.
  * содержащую счётчик значений меньших v, равных v и больших v*/
  def lteqqt(values: Array[Int], v: Int) = {
    val (lt, eqqt) = values.partition(_ < v)
    val (eq, qt) = eqqt.partition(_ == v)
    (lt.length, eq.length, qt.length)
  }

  /* Что произойдёт, если упаковать две строки, такие как "Hello".zip("World")?
  * Придумайте достаточно реалистичный случай испоьзования*/
  def ex10() = {
    val arr = "Hello".zip("World")
    arr.foreach { case (k, v) => println(f"$k $v") }
  }

}
