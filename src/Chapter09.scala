import _root_.java.io.File
import _root_.scala.io.Source
import _root_.java.io.PrintWriter
import _root_.scala.collection.mutable.ArrayBuffer

object Chapter09 extends App {

  /* 1. Напишите на языке Scala код, который размещает строки в файле в обратном порядке
   * (последнюю делает первой и т.ж.) */
  object FileReverse extends FileWorker {
    def apply(inFile: File, outFile: File): Unit = {
      val inLines = readFromFile(inFile, _.getLines.toBuffer)
      val outLines = inLines.reverse
      writeToFile(outFile, writer => outLines.foreach(writer.println(_)))
    }
  }


  /* 2. Напишите программу на языке Scala, которая читает файл с символами табуляции, заменяя их пробелами так,
   * чтобы сохранить расположение границ столбцов, и записывает результат в тот эе файл*/
  object TabReplace extends FileWorker {
    private val pattern = """\t""".r
    private val replacement: String = " " * 4

    def apply(file: File): Unit = {
      val inString = readStringFromFile(file)
      val outString = tabReplace(inString)
      writeToFile(file, _.print(outString))
    }

    def tabReplace(content: String): String = pattern.replaceAllIn(content, replacement)
  }

  /* 3. Напишите фрагмент кода на Scala, который читает файл и выводит в консоль все слова, содержащие 12
   * илт более символов. Дополнительные баллы начисляются тем, кто сможет сделать это в одной строке кода. */
  def printWords: Unit = Source.fromFile("file.txt").mkString.split("\\s+").foreach(word => if (word.length >= 12) println(word))


  /* 4. Напишите программу на Scala, которая читает тестовый файл, содержащий только
   * вещественные числа, выводит сумму, среднее, максимальное и минимальное значения. */
  object Numbers extends FileWorker {
    val delimiter: String = " "
    var numbers: Array[Double] = Array.empty[Double]

    def apply(file: File): Unit = {
      val inString = readStringFromFile(file)
      numbers = inString.split(delimiter).map(_.toDouble)
      printAverage()
      printSum()
      printMin()
      printMax()
    }

    def printAverage(): Unit = println(s"avr: ${numbers.sum / numbers.length}")

    def printSum(): Unit = println(s"sum: ${numbers.sum}")

    def printMin(): Unit = println(s"min: ${numbers.min}")

    def printMax(): Unit = println(s"max: ${numbers.max}")
  }


  /* 5. Напишите программу на Scala, которая записывает степени двойки и их обратные величины
   * в файл с экспонентой от 0 до 20. Расположите числа в столбцах. */
  object Power extends FileWorker {
    def apply(file: File): Unit = {
      val inSequence = createSequence()
      writeToFile(file, writer => inSequence.foreach(printTuple(writer, _)))
    }

    def createSequence(): IndexedSeq[(Int, Double)] = (1 to 20).map(createTuple)

    def createTuple(n: Int): (Int, Double) = (math.pow(2, n).toInt, math.pow(2, -n))

    def printTuple(writer: PrintWriter, tuple: (Int, Double)): Unit = tuple match {
      case (up, down) => writer.println(f"$up%10d\t$down")
    }
  }


  /* 6. Напишите регулярное выражение для поиска строк в кавычках "как эта, возможно с \" или \\".
   * Напишите программу на Scala, которая выводит все такие строки, найденные в файле с исходными текстами. */

  object Quotes extends FileWorker {
    val regex = """(?<![\|\\])"(.*?)(?<![\|\\])"""".r

    def apply(file: File): Unit = {
      val inString = readStringFromFile(file)
      printMatches(inString)
    }

    def printMatches(content: String): Unit = {
      val matches = regex.findAllIn(content)
      matches.foreach(println(_))
    }
  }


  /* 7. Напишите программу на Scala, которая читает текстовый файл и выводит все лексемы,
   * неявляющуиеся вещественными числами. Используйте регулярное выражение */
  object Lexemes extends FileWorker {
    val pattern = """[+-]?\d+\.\d+]"""

    def apply(file: File): Unit = {
      val inString = readStringFromFile(file)
      printMatches(inString)
    }

    def printMatches(content: String): Unit = {
      val matches = content.split("\\s+").filter(!_.matches(pattern))
      matches.foreach(println(_))
    }
  }

  /* 8. Напишите программу на Scala, которая выводит атрибуты src всех тегов img на веб-странице.
   * Используйте регулярные выражения и группы. */
  object Tags extends FileWorker {
    val regex = """<img.*?src=["'](.*?)["']""".r

    def apply(file: File): Unit = {
      val inString = readStringFromFile(file)
      printMatches(inString)
    }

    def printMatches(content: String): Unit = {
      val matches = regex.findAllIn(content)
      matches.foreach { case regex(url) => println(url) }
    }
  }


  /* 9. Напишите программу на Scala, которая подсчитывает количество файлов с расширением .class
   * в указанном каталоге и всех его подкаталогах. */
  def countFiles(dir: File, suffix: String = ".class"): Int = {
    val (directories, files) = dir.listFiles.partition(_.isDirectory)
    val length = files.count(_.getName.endsWith(suffix))
    length + directories.foldLeft(0)(_ + countFiles(_))
  }


  /* 10. Дополните пример сериализуемого класса Person возможностью сохранения коллекции друзей.
   * Создайте несколько объектов Person, сделайте некоторые из них друзьями других и затем сохраните массив
   * Array[Person] в файл. Прочитайте массив из файла и проверьте, не потерялись ли связи между друзьями. */
  class Person(val name: String) extends Serializable {
    private val friends = ArrayBuffer.empty[Person]

    import _root_.java.io._

    def addPerson(person: Person): Unit = friends += person

    def savePerson(file: File): Unit = {
      val out = new ObjectOutputStream(new FileOutputStream(file))
      try {
        out.writeObject(friends)
      } finally {
        out.close()
      }
    }

    def readPerson(file: File): Unit = {
      val in = new ObjectInputStream(new FileInputStream(file))
      try {
        in.readObject().asInstanceOf[ArrayBuffer[Person]]
      } finally {
        in.close()
      }
    }
  }

  /* ******************************************************************************* */
  class FileWorker {

    protected def readFromFile[T](file: File, func: Source => T): T = {
      val source = Source.fromFile(file)
      try {
        func(source)
      } finally {
        source.close()
      }
    }

    protected def readStringFromFile(file: File): String = readFromFile(file, _.mkString)

    protected def writeToFile(file: File, func: PrintWriter => Unit): Unit = {
      val writer = new PrintWriter(file)
      try {
        func(writer)
      } finally {
        writer.close()
      }
    }


  }

}
