import scala.annotation._
import java.io.{File, IOException}
import scala.io.Source
import java.io.IOException

object Chapter15 extends App {

  /* 1. */


  /* 2. Напишите класс, демонстрирующий все возможные способы размещения аннотаций.
   * В качесьве образцовой используйте аннотацию @deprecated */
  @deprecatedInheritance("Message", "Since")
  class Example {
    @deprecatedOverriding("Message", "Since")
    def first(@deprecatedName('v, "Since") value: Int): Unit = {}
  }


  /* 3. Какие из аннотаций в библиотеке Scala используют какую-либо из метааннотаций:
   * @param, @field, @getter, @setter, @beanGetter и @beanSetter */
  val annotations = Array("@deprecated", "@deprecatedName", "@BeanProperty")

  /* 4. Напишите метод sum с переменным числом целочисленных аргументов,
   * возвращающий сумму своих аргументов. Вызовите его из Java. */
  @varargs def sum(args: Int*): Int = args.sum


  /* 5. Напишите метод, возвращающий строкове значение с содержимым текстового файлы.
   * Вызовите его из Java. */
  @throws(classOf[IOException])
  def read(file: File) = {
    val in = getClass.getResourceAsStream(file.getPath)
    if (in == null) throw new IOException("Resource is not found: " + file)
    Source.fromFile(file).mkString
  }


  /* 6. */


  /* 7. Приведите пример, демонстрирующий, почему оптимизация хвостовой рекурсии не может
   * быть произвдеена, если метод допускает возможность прееопределения. */
  class TailRec {
    @tailrec final def sum(lst: List[Int], acc: Int = 0): Int = lst match {
      case head :: tail => sum(lst, acc + head)
      case _ => acc
    }
  }


  /* 8. Добавьте метод allDifferent в объект, скопмилируйте и загляните в байт-код.
   * Какие методы будут сгенерированы после применения аннотации @specialized? */
  def allDifferent[@specialized T](x: T): T = x


  /* 9. */

  /* 10. Добавьте вызов assert(n>=0) в метод factorial. Скомпилируйте, разрешив выполнение
   * проверок, и убедитесь, что вызов factorial(-1) генерирует исключение. Скомпилируйте,
   * запретив выполнение проверок. Что изменилось? Используйте javap для выявления
   * изменений в точках вызовов проверок. */
  def factorial(n: Int): Int = {
    assert(n >= 0, "Some message")
    if (n <= 0) 1
    else n * factorial(n - 1)
  }

  //-Xelide-below MAXIMUM


}
