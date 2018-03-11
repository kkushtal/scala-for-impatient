object Chapter03 extends App {

  object Exercise01 {
    val question = "Напишите фрагмент кода, который записывает в массив (с имененем) 'a' целые числа в диапазоне от 0 (включительно) до n (исключая его)."

    import util.Random

    def answer(n: Int) = {
      val a = new Array[Int](n)
      for (i <- 0 until n) a(i) = Random.nextInt(n)
      a
    }
  }


  object Exercise02 {
    val question = "Напишите цикл, меняющий местами смежные элементы в массиве целых чисел. Например, Array(1, 2, 3, 4, 5) должен стать Array(2, 1, 4, 3, 5)."

    def answer(a: Array[Int]) = {
      for (i <- 0 until a.length - 1 by 2) {
        val v = a(i)
        a(i) = a(i + 1)
        a(i + 1) = v
      }
      a
    }
  }

  object Exercise03 {
    val question = "Повторите предыдущее упражнение, но создайте новый массив с представленными элементами. Используйте выражение for/yield."

    def answer(a: Array[Int]) = {
      val res = for (i <- 0 until a.length) yield {
        if (i % 2 == 1) a(i - 1)
        else if (i + 1 < a.length) a(i + 1)
        else a(i)
      }
      res.toArray
    }
  }

  object Excercise04 {
    val question = "Дан массив целых чисел, создайте новый массив, в котором сначала будут следовать положительные элементы из оригинального массива, в оригинальном порядке, а за ними отрицательные и нулевые значения, тоже в оригинальном порядке."

    def answer(a: Array[Int]) = {
      val (pos, neg) = a.partition(_ > 0)
      pos ++ neg
    }
  }

  object Excercise05 {
    val question = "Как бы вы вычислили среднее значение элементов массва Array[Double]?"

    def answer(a: Array[Double]) = a.sum / a.length
  }

  object Excercise06 {
    val question = "Как бы вы переупорядочили элементы массива Array[Int] так, чтобы они следовали в обратом отсортированном порядке? Как бы вы сделали то же самое с буфером ArrayBuffer[Int]?"

    import collection.mutable.ArrayBuffer

    def answer(a: Array[Int]) = {
      a.sorted.reverse
    } //scala.util.Sorting.quickSort(a); a.reverse
    def answer(b: ArrayBuffer[Int]) = b.sortWith(_ > _)
  }

  object Excercise07 {
    val question = "Напишите фрагмент программного кода, выводящий значения всех элементов из массива, кроме повторяющихся. (Подсказка: загляните в Scaladoc.)"

    def answer[T](a: Array[T]) = a.distinct.mkString(", ")
  }

  object Excercise08 {
    val question = "Соберите индексы отрицательных элементов, расположите их в обратном порядке, отбросьте последний индекс и вызовите a.remove(i) для каждого индекса."

    def answer(a: Array[Int]) = {
      val b = a.toBuffer
      val indexes = (for (i <- 0 until b.length if a(i) < 0) yield i).reverse.dropRight(1)
      for (i <- indexes) b.remove(i)
      b.toArray
    }
  }

  object Excercise09 {
    val question = "Создайте коллекцию всех часовых поясов, возвращаемых методом java.util.TimeZone.getAvailableIDs для Америки. Отбросьте префикс 'America/' и отсортируйте результат."

    import java.util.TimeZone

    def answer(prefix: String = "America/") = {
      val a = java.util.TimeZone.getAvailableIDs()
      a.filter(_.startsWith(prefix)).map(_.stripPrefix(prefix)).sorted
    }
  }

  object Excercise10 {
    val question = "Импортируйте java.awt.datatranswer._ и создайте объект типа SystemFlavorMap вызовом " +
      "var flavors = SystemFlavorMap.getDefaultFlavorMap()" +
      "asInstanceOf[SystemFlavorMap]" +
      "Затем, вызовите метод getNativesForFlavor с параметром Data-Flavor.imageFlavor и получите возвращаемое значение как буфер Scala. " +
      "(Зачем нужен непонятный класс? Довольно сложно найти пример использования java.uril.List в стандартной библиотеке Java)."

    import java.awt.datatransfer._
    import scala.collection.JavaConversions.asScalaBuffer
    import scala.collection.mutable.Buffer

    def answer = {
      val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
      val res: Buffer[String] = flavors.getNativesForFlavor(DataFlavor.imageFlavor)
    }
  }

}
