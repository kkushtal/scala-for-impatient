object Chapter18 extends App {

  /* 1. Определите неизменяемый класс Pair[T, S] с методом swap,
   * возвращающим новую пару, где компоненты поменяны местами */
  class Pair1[T, S](val first: T, val second: S) {
    def swap: Pair1[S, T] = new Pair1(second, first)
  }


  /* 2. Определите изменяемый класс Pair[T] с методом swap,
   * который меняет компоненты пары местами. */
  class Pair2[T](var first: T, var second: T) {
    def swap: Pair2[T] = {
      val tmp = first
      first = second
      second = tmp
      this
    }
  }


  /* 3. Для класса Pair[T, S] напишите обобщённый метод swap, который принимает пару
   * в виде аргумента и возвращает новуб пару с компонентами, поменянными местами. */
  def swap[T, S](pair: Pair1[T, S]): Pair1[S, T] = {
    new Pair1(pair.second, pair.first) //pair.swap
  }


  /* 4. Почему не требуется объявлять верхнюю границу в методе replaceFirst в разделе 18.3.
   * "Границы представления типов", когда предполагается заменить первый компонент
   * в экземпляре Pair[Person] экземпляром Student? */
  class Pair4[T](val first: T, val second: T) {
    def replaceFirst[R >: T](newFirst: R) = new Pair4(first, newFirst)
  }

  object Pair4 {

    class Person

    class Student extends Person

    def replaceFirst(student: Student = new Student): Pair4[Person] = {
      val pair = new Pair4(new Person, new Person)
      pair.replaceFirst(student)
    }
  }


  /* 5. Почему RichInt использует Comparable[Int], а не Comparable[RichInt]? */


  /* 6. Напишите обобщённый метод middle, возвращающий средний элемент из любого
   * экземпляра Iterable[T]. Например, вызов middle("World") должен вернуть 'r'. */
  def middle[A, C](input: C)(implicit ev: C => Iterable[A]): A = {
    val length = input.size
    val average = length / 2
    input.toSeq(average)
  }


  /* 7. Посмотрите список методов трейта Iterable[+A]. Какие из них используют параметр тип А?
   * Почему в этих методах он находится в ковариантной позиции? */
  def methods[A, R](input: A)(implicit ev: A => Iterable[R]): R = {
    //Function1[-A, +R]
    var `+R` = input.head
    /*`+R` = input.last
    `+R` = input.max
    `+R` = input.min
    `+R` = input.sum
    `+R` = input.product*/
    `+R`
  }


  /* 8. В разделе 18.10. "Ко- и контрвариантные позиции" в методе replaceFirst определена
   * граница типа. Почему нельзя определить эквивалетный метод для изменяемого класса Pair[T]?
   * def replaceFirst[R >: T](newFirst: R) { first = newFirst } */
  /* val pair = new PairD[Student, Person](student1, student2) */
  class Pair8[R, T](var first: R, var second: T)(implicit ev: T <:< R) {
    def replaceFirst(newFirst: R): Unit = first = newFirst

    def replaceFirstSub[V <: R](newFirst: V): Unit = first = newFirst
  }


  /* 9. На первый взгляд, кажется странной необходимость ограничивать параметры метода
   * неизменяемого класса Pair[+T]. Но представьте, что в Pair[T] можно написать такое
   * определение метода: def replaceFirst(newFirst: T)
   * Проблема в том, что подобный метод можно переопределить не совсем правильным способом.
   * Придумайте пример проблемы. Напишите подкласс NastyDoublePair класса Pair[Double], переопределяющий
   * метод replaceFirst, который создаёт пару, где первый элемент является результатом извлечения
   * квадратного корня из аргумента newFirst. Затем сконструируйте вызов метода replaceFirst("Hello")
   * типа Pair[Any], который в действительности является типом NastyDoublePair. */
  class Pair9[T](val first: T, val second: T) {
    def replaceFirst(newFirst: T): Pair9[T] = new Pair9(newFirst, second)
  }

  class NastyDoublePair(first: Double, second: Double) extends Pair9[Double](first, second) {
    override def replaceFirst(newFirnst: Double): Pair9[Double] = new Pair9(math.sqrt(newFirnst), second)
  }

  def check(pair: Pair9[Any]): Unit = pair.replaceFirst(10)


  /* 10. Для изменяемого класса Pair[S, T] используйте механизм ограниения типа,
   * чтобы определить метод swap, который можно вызывать с параметрами одного типа. */
  class Pair10[S, T](var first: S, var second: T) {
    def swap(implicit ev: T =:= S): Pair10[S, T] = {
      val tmp = first.asInstanceOf[T]
      first = second
      second = tmp
      this
    }
  }
}
