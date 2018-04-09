import java.net.InetSocketAddress
import java.util.Calendar

import javax.swing.text.Document

import scala.collection.SortedSet
import scala.collection.parallel.ParSet
import scala.annotation._
import scala.annotation.elidable._
import _root_.scala.concurrent.{ExecutionContext, Future, Promise}
import ExecutionContext.Implicits.global
import _root_.scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import java.time.LocalTime
import java.util.concurrent.Executors
import _root_.scala.language.implicitConversions
import _root_.scala.io.Source
import _root_.java.io.File

object Example extends App {

/*
  abstract class <!<[-From, +To] extends Function1[From, To]

  object <!< {
    implicit def doSome[A] = new (A <!< A) { def apply(x: A) = x }
  }
  abstract class fn[-From, +To] extends Function1[From, To]
  object fn {
    def doSome[A] = new (A fn A) { def apply(x: A) = x }
  }
  fn(1,1)*/

  /*def doSome[T <: Iterable[T]](x: T) = {}
  class impl[T: Ordering](val x: T, y: T) {
    val ev: Ordering[T] = implicitly[Ordering[T]]
    def compare(): Unit = {
      import _root_.scala.Ordered._
      ev.compare(x, y)
      x < y
    }
  }*/
  /*class Fraction(val num: Int, val den: Int) {
    def *(other: Fraction): Fraction = new Fraction(num * other.num, den * other.den)
    override def toString: String = s"$num/$den"
  }

  object Fraction {
    def apply(num: Int, den: Int): Fraction = new Fraction(num, den)
    implicit def int2Fraction(from: Int): Fraction = Fraction(from, 1)
    implicit def Fraction2int(from: Fraction): Int = from.num
    //implicit def fraction2Double(f: Fraction): Double = f.num * 1d / f.den
    //implicit def int2Fraction(n: Int): Fraction = Fraction(n, 1)
  }

  object FractionConversions {
    implicit def int2Fraction(n: Int): Fraction = Fraction(n, 1)
    implicit def fraction2Double(f: Fraction): Double = f.num * 1d / f.den
    implicit def double2Fraction(d: Double): Fraction = Fraction(d.toInt, 1)
  }
  //import FractionConversions.{double2Fraction, fraction2Double, int2Fraction}
  //import FractionConversions.{fraction2Double => _, _}
  val fr = Fraction(2, 3)
  val vInt = fr * 3
  val vFr = 3 * fr
  println(vInt)
  println(vFr)*/
  //import FractionConversions.fraction2Double
  //val resDouble = 3d * fr
  //import FractionConversions.int2Fraction
  //val resFraction: Fraction = fr * 3//int2Fraction(3) * fr
  //println(s"resDouble: $resDouble")
  //println(s"resFraction: $resFraction")


  /*implicit class RichFile(val from: File) extends AnyVal {
    def read: String = Source.fromFile(from.getPath).mkString
  }

  val file: File = new File("path.txt")
  val contents = file.read*/


  /*class Person

  class Student extends Person

  val person1 = new Person
  val person2 = new Person
  val student1 = new Student
  val student2 = new Student*/

  /*val pair = new PairD[Student, Person](student1, student2)
  pair.replaceFirst(person1)*/


  /*import _root_.scala.collection.mutable.ArrayBuffer
  trait Friend[-T] {
    def befriends(someone: T)
  }

  def makeFriendWith(student: Student, friend: Friend[Student]): Unit = friend.befriends(student)

  class Person extends Friend[Person] {
    val friends: ArrayBuffer[Person] = ArrayBuffer.empty[Person]
    override def befriends(someone: Person): Unit = friends += someone
  }
  class Student extends Person

  val susan = new Student
  val sven = new Student
  val fred = new Person

  val v: Unit = makeFriendWith(susan, fred)
  fred.befriends(sven)

  def friends(students: Array[Student], find: Student => Person): Array[Person] = {
    students.map(find)
  }

  def findStudent(p: Person): Student = p.asInstanceOf[Student]*/
  /*
    def create(arg: Int): (Future[Int], Promise[Int]) = {
      val promise = Promise[Int]()
      val future = Future {
        Thread.sleep(5 * 1000)
        var n = arg * 2
        println(s"${LocalTime.now()}; Future: $n ")

        promise.success {
          println(s"${LocalTime.now()}; Promise: $n ")
          n
        }

        Thread.sleep(5 * 1000)
        n = arg * 3
        println(s"${LocalTime.now()}; Future: $n ")
        n
      }
      (future, promise)
    }

    val n = 2
    val promise = Promise[Int]()

    def future = Future {
      val v = n * 2
      val res = v * 3
      promise.success(1)
      promise.success(res)

      res
    }

    val ec = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
    val r = Future.traverse(1 to 3)(x => Future {
      Thread.sleep(5000)
      val v = x * 2
      println(s"$v. ${LocalTime.now()}")
      v
    })*/


  /*  def doSome(arg: Int) = arg * 2

    def answer(arg: Int) = {
      val p = Promise[Int]()
      Future {
        val n = doSome(arg)
        p.success(n)
        10
      }
      p.future
    }

    def answer2(arg: Int) = {
      val p = Promise[Int]()
      Future {
        val n = doSome(arg)
        p.success(n)
      }
      p.future
    }*/


  /*val a = Future(5)
  val b = Future(6)
  val c = Future(7)

  Future.sequence(Vector(a, b, c)) map (_.product) foreach println
  val features = Vector(a, b, c)
  features.find(_.isCompleted)
  Future.firstCompletedOf(features)

  val map = Map((1,"!"), (2,"@"), (3,"#"))
  val fs = Future.traverse(map){case (k, v) => Future(v * k)}
  fs.map(_.mkString(", ")).foreach(println)

  Future.foldLeft(features)(0)(_ * _)*/


  /*val f1 = Future {
    print("1. FUTURE !!!")
    10
  }
  def f2 = Future {
    print("2. FUTURE !!!")
    20
  }

  val r = for (v1 <- f1; v2 <- f2 if v1 == v2) yield v1 + v2

  val arr = List(0, 1)
  arr.flatMap(v => Option(v + 1))

  def square(x: Int): Option[Int] = Some(x * x)
  def increment(x: Int): Option[Int] = Some(x + 1)
  def unit(x: Int): Option[Int] = Some(x)
  def leftUnitLaw(): Unit = {
    val x = 5
    val monad: Option[Int] = Some(x)
    val result = monad.flatMap(square) == square(x)
    print(result)
  }
  def rightUnitLaw(): Unit = {
    val x = 5
    val monad: Option[Int] = Some(x)
    val result = monad.flatMap(unit) == monad
    print(result)
  }
  def associativityLaw(): Unit = {
    val x = 5
    val monad: Option[Int] = Some(x)
    val left = monad flatMap square flatMap increment
    val right = monad flatMap(x => square(x) flatMap increment)
  }

  def findPort(): Option[Int] = Some(80)
  def findHost(): Option[String] = Some("my.host.com")

  import java.net.InetSocketAddress
  val address: Option[InetSocketAddress] = for {
    port <- findPort()
    host <- findHost()
  } yield new InetSocketAddress(host, port)

  val address2: Option[InetSocketAddress] = findHost().flatMap(host => findPort().map(port => new InetSocketAddress(host, port)))
  address.foreach(println)
  address2.foreach(println)

  val t = Try{throw new Exception("!"); 20}
  t.getOrElse("?")*/


  /*def block1 = {
    println("block1")
    Thread.sleep(1000)
    10 //throw new Exception("FIRST MESSAGE THROW")
  }

  val f1 = Future {
    block1
  }

  def block2 = {
    println("block2")
    Thread.sleep(2000)
    20 //throw new Exception("SECOND MESSAGE THROW")
  }

  def f2(arg: Int) = Future {
    block2
  }
  //complete(f1, f2)

  val combined = f1.flatMap(v1 => f2(v1).map(v2 => v1 + v2))
  combined

  def complete(f1: Future[Int], f2: Future[Int]): Unit = f1.onComplete {
    case Success(v1) => f2.onComplete {
      case Success(v2) => print(s"value is: ${v1 + v2}")
      case Failure(ex) => print(s"2. ${ex.getMessage}")
    }
    case Failure(ex) => print(s"1. ${ex.getMessage}")
  }*/


  /*f1.onComplete {
    case Success(v1) => f2.onComplete {
      case Success(v2) => print(s"value is: ${v1 + v2}")
      case Failure(ex) => print(s"2. ${ex.getMessage}")
    }
    case Failure(ex) => print(s"1. ${ex.getMessage}")
  }*/


  /*val value = 3
  val future = Future {
    Thread.sleep(10000)
    if (value > 2) true
    else throw new Exception("My Message")
  }
  val waitFuture = Await.ready(future, 10.seconds)

  val Some(valueTry) = if (waitFuture.isCompleted) waitFuture.value else None
  val valueOption = valueTry.toOption

  val res = (valueTry: @switch @unchecked) match {
    case Success(x) => x.toString
    case Failure(ex) => ex.getMessage
  }
  print(res)*/


  /*@tailrec def sum(part: Int)(seq: Seq[Int]): Int = {
    if (seq.isEmpty) 0 else sum(seq.head + part)(seq.tail)
  }*/
  /*val value = "Value"
  val result = (value: @unchecked) match {
    case _: String => "STRING..."
  }*/

  /*@deprecatedInheritance class Dep {
    @deprecatedOverriding(message = "MESSAGE")
    def some() {}
  }
  class xDep() extends Dep{
    override def some(): Unit = super.some()
  }*/
  /*object sum {
    @deprecated(message="My MESSAGE", since = "BECAUSE")
    @tailrec def apply(@deprecatedName('accum) acc: Int)(seq: Seq[Int]): Int = {
      if (seq.isEmpty) acc else sum(acc + seq.head)(seq.tail)
    }
  }

  val s0 = sum(accum = 0)(1 to 10)
*/
  //@elidable(elidable.ASSERTION) def func[@specialized(Int, Double) T, @specialized(Boolean) B](value: T, bool: B): B = bool

  /*val s0 = sum(0)
  s0(Array(1, 2, 3, 4, 5, 6))*/
  /*@tailrec def suM(seq: Seq[Int]): Int = {
    if (seq.isEmpty) 0 else seq.head + suM(seq.tail)
  }*/

  /*class ToJava {
    import scala.annotation._
    //import scala.beans.BooleanBeanProperty
    @throws(classOf[java.io.IOException]) def read(file: java.io.File) = {}
    @scala.beans.BooleanBeanProperty val value: Boolean = true
  }

  object Object {
    @scala.beans.BooleanBeanProperty private val value: Boolean = true
  }*/
  /*def trycatch[T](block: => T)(catcher: PartialFunction[Throwable, T]): T ={
    try block
    catch catcher
  }

  val x = 0
  trycatch(x + 1) { case _: NumberFormatException => x }*/

  /*val map = Map(1 -> "!", 2 -> "@")
  val res = map.get(2) match {
    case Some("!") => "first"
    case Some("@") => "second"
    case _ => "other"
  }*/

  /*val map = Map((1, 10), (2, 20))
  val arr = Array(Some(1), None, Some(2))
  val v = map.get(3)
  v.filter(_ > 5)*/

  /*val pattern = """\{[(0-9)+]\}""".r
  val message = "Some {0} message with {1} text without {2} other {3} function in {4} Scala"
  val replace = Map(("{0}", "One"), ("{1}", "Two"), ("{2}", "Three"), ("{3}", "Four"))
  val lift = replace.lift
  val result = pattern.replaceSomeIn(message, m => lift(m.matched))
  print(result)*/

  /*val func: PartialFunction[Int, Boolean] = {
    case 1 => true
    case 2 => true
    case _ => false
  }
  val less: PartialFunction[Int, Boolean] = {
    case value => value < 5
  }

  val arr = Array(1, 2, 3, 4, 5)
  val f = less.lift
  print(arr.filter(less).mkString(", "))
  val map = Map((0, "!"), (1, "@"), (2, "#"))
  val map2 = Map((2, "@"), (3, "#"))
  val fm = map.lift
  println(fm(1).get)

  print(map.keys.collect(arr).mkString(", "))*/

  /*val f = func.lift
  print(f(1) + " " + func(1))
  func.isDefinedAt(1)*/


  /*arr.flatten
  map.collect { case (Some(key), value) => key -> value }
  map.view.filter(_._1.isDefined).map { case (Some(k), v) => (k, v) }*/

  /*sealed abstract class TLC
  case object Red extends TLC
  case object Yellow extends TLC
  case object Green extends TLC
  val color = new TLC {}
  color match {
    case Red => "red"
    case Green => "green"
    //case Yellow => "yellow"
    case _ => "??"
  }

  val v: Option[Int] = Some(1)
  v.get*/

  /*case class Example() {
    class Input{}
  }
  val e = new Example
  val e2 = new Example
  new e.Input*/

  /*trait Item

  case class Book(title: String, price: Double) extends Item

  case class Package(description: String, discount: Double, books: Item*) extends Item

  val books =
    Package("First Package", .1,
      Book("First Book", 10d),
      Book("Second Book", 20d),
      Package("Second Package", .1,
        Book("Second-First Book", 100d),
        Book("Second-Second Book", 200d)
      )
    )

  def getPrice(item: Item): Double = item match {
    case Book(_, price) => price
    case Package(_, discount, books@_*) => books.map(getPrice).sum - discount
  }

  val Package(_, _, first_book, second_book @ Book(_: String, value: Double), Package(_, _, second_books @ _*)) = books*/


  /*class Name(val name: String, val value: Int) {
    var x = 0
    def copy(name: Option[String] = None, value: Option[Int] = None): Name = {
      val _name: String = if (name eq None) this.name else name.get
      val _value: Int = if (value eq None) this.value else value.get
      new Name(_name, _value)
    }
  }

  case class OP(first: String, second: String)

  val op = OP("!", "@")

  val res = op match {
    case OP(first, second) => f"first: $first; second: $second"
    case _ => ""
  }


  case class ?#()
  case object ++ {
    def unapply(arr: Array[String]): Option[(String, String)] = {
      if (arr.length < 2) None
      else Some(arr(0), arr(1))
    }
  }



  val n ++ m = Array("first", "second")*/
  /*val name = new Name("Ivan", 10)
  name.x = 1
  val name2 = name.copy(value = Some(1))
  print(f"${name.value}; ${name2.value}; ${name2.x}")*/


  /*object FindAs {
    val symbol: String = "/file/"

    def unapplySeq(paths: Array[String]): Option[IndexedSeq[String]] = {
      val res = paths.filter(_.contains(symbol))
      if (res.nonEmpty) Some(res)
      else None
    }
  }

  var arr = Array("C:/file/path.txt", "C:/files/file.txt", "C:/file/index.html")
  val res = arr match {
    /*case FindAs(a, b, z @_*) => f"a: $a, b: $b, z: ${z.length}"
    case FindAs(a, b) => f"a: $a, b: $b"*/
    case FindAs(a, _*) => f"a: $a"
    case _ => "None!"
  }
  //print(arr.mkString(", "))
  //val res = arr.mkString(", ")
  //print(res)
  val regex = "([0-9]+) ([a-z]+)".r
  val value = "7592 ivan"
  //val (number, name) = regex.findAllIn(value)
  val r = value match {
    case regex(number, name) => f"$name $number"
    case _ => "None"
  }
  print(r)
  */
  /*var array = Array(1, 2, 3, 4, 5)
  val res = array match {
    case Array(x, y) => f"x: $x; y: $y"
    case Array(x, _, y, z@_*) => f"x: $x; y: $y; z: ${z.mkString(", ")}"
    case _ => "None"
  }
  println(res)*/

  /*object Arr {
    def unapplySeq(arr: Array[Int]): Option[IndexedSeq[Int]] = {
      if (arr.nonEmpty) Some(arr)
      else None
    }
  }

  val lst = Array(1, 2, 3, 4, 5)
  val Arr(first, arr @ _*) = lst
  println(arr.mkString(", "))

  val f = Arr.unapplySeq(lst)*/


  /*val str = "my string in scala language"
  val arr = Array((0 to 2).toArray, (3 to 4).toArray, (5 to 6).toArray, (7 to 8).toArray, (9 to 10).toArray).par
  val res = arr.fold(Array.empty[Int])(_ ++ _)
  print(res.mkString(", "))*/

  /*val strings = List("abc","def","ghi","jk","lmnop","qrs","tuv","wx","yz").par

  val alphabet = strings.reduce(_++_)
  //print(alphabet)
  //val a = Array((1 to 100): _*)
  val arr = (1 to 1000).toArray
  val arr2 = Array.tabulate(1000)(x => x).par
  val arr3 = for (k <- arr2) yield {
    //print(k + " ")
    k
  }
  /*println("")
  print(arr3)*/
  val a = Array.tabulate[Int](10)(_.toInt)
  a.fold(0.0)((acc, x) => 9.0 + 0.0)
  val set = ParSet(("Name", 10.0, 5), ("Name2", 15.0, 6), ("Name3", 20.0, 7))
  def doSome(acc: Double, price: Double, count: Int): Double = acc + price * count

  val res = set.aggregate(0d)((acc, tuple) => doSome(acc, tuple._2, tuple._3), _ + _)
  print(res)*/


  /*import scala.collection.JavaConverters._
    val map = System.getProperties().asScala
    val arr = 1 to 100
    val v = arr.view
    val vp = v.par.map(x => {println(x); x}).map(x => { val v = -x; println(v); v})*/
  /*val arr = List(1, 2, 3, 4, 5)
  arr.map(x => {
    val v = x * 2
    println(v)
    v
  }).map(x => {
    val v = x * -1
    println(v)
    v
  })


  arr.view.map(x => {
    val v = x * 2
    println(v)
    v
  }).map(x => {
    val v = x * -2
    println(v)
    v
  }).force*/


  /*val view = (10 to 20).view
  var v2 = view.map(_ * 2)
  val v3 = v2.map(math.pow(_, 2).toInt)
  v3.force*/


  /*  def nums(n: BigInt): Stream[BigInt] = n #:: nums(n)
    val stream = nums(10)*/

  //val set = str.split("\n").toSet//.flatMap(_.toCharArray)
  //val res = set.flatMap(_.toCharArray)
  //val s = SortedSet(a: _*)
  /*val arr = Array(1,2,3,4,5)
  val set1 = Set(1,2,3)
  val set2 = Set(3,4,5)
  val res = arr match {
    case last +: arr.tail => {println(last); true}
    case _ => false
  }
  print(res)
  */


  /*import _root_.scala.collection.mutable.LinkedList
  val lst = LinkedList(-1, -2, 3, -4, -5, 6)

  def doSome(lst: LinkedList[Int], fn: (Int) => Boolean): Unit = {
    var cur = lst
    while (cur != Nil) {
      if (fn(cur.elem)) cur.elem *= -1
      cur = cur.next
    }
  }

  def del(lst: LinkedList[Int]): Unit = {
    var cur = lst
    while (cur != Nil && cur.next != Nil) {
      cur.next = cur.next.next
      cur = cur.next
    }
  }*/

  /*def run(fn: => Boolean)(block: => Int): Int = {
    if (fn) block
    else 0
  }

  val fn = 1 == 1

  val block = 10 - 2
  print(run(fn)(block))*/


  /*val fn = (x: Int) => x * 2
  val arr = Array(1, 2, 3, 4)
  arr.map { (x: Int) => x * 2 }
  arr.filter { x => x.equals(0) }

  def func[T](fn: T => T): T => T = fn
  val fnInt = (_: Int) * 3
  func(fnInt)

  def mulBy(x: Int) = x * (_: Int)
  val x3 = mulBy(3)
  val res = x3(55)

  def f[T] = _: T
  val fInt = f[Int]
  print(fInt(1))*/


  /*val a = Array(1, 2, 3)
  val b = Array(1, 2, 4)
  a.corresponds(b)(_ < _)
  def ff(n: Int) = n * (_: Int)
  def ff2(n: Int)(m: Int) = n * m
  object obj {
    var n = "!"
    def apply(m: String): String => String = x => (n, m, x).productIterator.mkString(" ")
  }
  val func = obj("*")
  println(f"1: ${func("?")}")
  obj.n = "#"
  println(f"2: ${func("?")}")



  val mulBy = new Function[Double, Double => Double] {
    def apply(_n: Double): Double => Double = {
      new Function[Double, Double] {
        val n = _n
        def apply(x: Double): Double = n * x
      }
    }
  }
  val half = mulBy(.5)
  val triple = mulBy(3)
  val res1 = half(20)
  val res2 = triple(10)*/

  /*trait Logger {
    print(f"Logger => ")
    def log(msg: String): Unit = {}

    // = println(f"Logged: $msg")
    def info(msg: String, add: String = "INFO"): Unit = log(f"$add: $msg")

    def warn(msg: String, add: String = "WARN"): Unit = log(f"$add: $msg")

    def severe(msg: String, add: String = "SEVERE"): Unit = log(f"$add: $msg")
  }

  trait ConsoleLogger extends Logger {
    abstract override def log(msg: String): Unit = println(f"ConsoleLogger: $msg")
  }

  trait ShortLogger extends Logger {
    print(f"ShortLogger => ")
    val maxLength: Int = 10
    var addString: String = "..."

    def cutString(msg: String): String =
      if (msg.length > maxLength)
        msg.take(maxLength - addString.length) + addString
      else msg

    override def log(msg: String): Unit = super.log(cutString(msg))
  }

  trait TimestapLogger extends Logger {
    print(f"TimestapLogger => ")
    /*val mLength: Int
    val array = new Array(mLength)*/
    override def log(msg: String): Unit = super.log(f"${Calendar.getInstance().getTime()} $msg")
  }

  class Account {
    print(f"Account => ")
    var balance: Int = 0
  }

  class SavingsAccount extends Account with ShortLogger with TimestapLogger {
    override def log(msg: String): Unit = println(f"!!$msg!!")
  }

  class BankAccount extends Account with Logger /*with ShortLogger with TimestapLogger */ {
    print(f"BankAccount => ")
    override def log(msg: String): Unit = println(f"BankAcoount: $msg")

    def withdraw(amount: Int): Unit = {
      if (balance < amount) severe("Withdraw error.")
      else balance -= amount
    }
  }*/

  /*class A {
    print("A")
    def getMessage(): String = "MESSAGE"
  }
  class B extends A {
    print("B")
    def methodB() = "B"
  }
  trait C extends B {
    outer: A =>
      print("C")
      def n = 10
      val arr = new Array(n)
      println(outer.getMessage())
      def log(): Unit = print(outer.getMessage())
      def save(): Unit = print(f"save: ${outer.getMessage()}")
  }
  class D extends C {
    print("D")
    override def n = 5
    override def getMessage() = "!! MESSAGE !!"
    //def getMessage(): String = "MESSAGE"
  }
  val d = new D
  d.log()
  d.save()
  print(d.arr.length + " " + d.methodB())*/


  /*val acc = new BankAccount with TimestapLogger with ShortLogger {
    override def log(msg: String): Unit = println(f"!!$msg!!")
  }
  println
  acc.withdraw(100)

  val acc2 = new SavingsAccount
  acc2.log("MESSAGE")*/

  /*trait Parent {
    print("Parent => ")
    def show(msg: String): Unit
  }

  class subParent extends Parent {
    print("subParent => ")
    def show(msg: String): Unit = print(f"\nsubParent: $msg")
  }

  trait otherSubParent extends Parent {
    print("otherSubParent => ")
    abstract override def show(msg: String): Unit = {super.show(msg); print(f"\notherSubParent: $msg")}
  }

  /*val obj = new subParent with otherSubParent
  obj.show("MESSAGE")*/



  trait A {
    def log(msg: String): Unit //= print(f"A$msg")
  }

  trait B extends A {
    val n = 10
    abstract override def log(msg: String): Unit = super.log(f"B$msg")
  }

  trait C extends A {
    val n: Int
    val arr: Array[Int] = new Array[Int](n)
    abstract override def log(msg: String): Unit = super.log(f"C$msg")
  }

  trait E {
    def log(msg: String): Unit = print(f"E$msg")
  }

  class D(val n: Int = 10) extends A {
    override def log(msg: String): Unit = print(f"D$msg")
  }


  class F {}
  val d = new D(5) with C


  trait _A {
    val range: Int = 10
    val arr: Array[Int] = new Array[Int](range + 1)
  }
  class _B {
  }

  val _b = new {override val range = 5} with _B with _A
  print(_b.arr.length)
  */


  //val acc = new { val mLength = 5; } with BankAccount with ShortLogger with TimestapLogger { print(array.length)}
  //acc.withdraw(100)

  /*trait A {
    val size: Int
    val array = new Array(size)
  }
  class B(var _size: Int) extends { override val size = _size} with A {
  //    val size = _size
    println(f"length: ${array.length}; size: $size")
  }
  val s = 11
  val b = new {override val size = s} with B(5)
  print(b.size)*/


  /*val acc = new {
    val p = 99
  } with BankAccount {
    print(f"Anonymous => ")
  }*/
  /*with TimestapLogger with ShortLogger*//* {
    println(f"Anonymous: ${this.getClass().getName()}")
    //override def log(msg: String): Unit = println(f"override: $msg")
    lazy val className = super.getClass().getName()
    override def log(msg: String): Unit = super.log(f"$className: $msg")
    val maxLength: Int = 30
    addString = " ... ?"
  }*/
  //


  /*class Number(val amount: Int) {

  }
  object Number {
    def apply(amount: Int): Number = new Number(amount)
    implicit def compare(n: Number): Ordered[Number] = Ordered[Number]
  }
  def func[T](x: T, x2: T)(implicit order: T => Ordered[T]): Boolean = {
    x < x2
  }
  val n = Number(1)
  val n2 = Number(2)
  val res = func(n, n2)

  println(res)*/


  /*abstract class Person {
    type Type
    def doSome: Type
  }
  class Student extends Person {
    type Type = String
    def doSome: Type = ""
  }*/
  /*class Child extends Student {
    override type Type = Int
    override def doSome: Type = 0
  }*/

  /*trait ExamplePerson {
    type Type <: Person
  }
  class ExampleP extends ExamplePerson {
    type Type = Student
  }*/
  /*  object Title

    object Article

    class Document {
      var useNextArgAs: Any = null
      var title: String = ""
      var article: String = ""

      def set(obj: Title.type): this.type = {
        useNextArgAs = obj;
        this;
      }

      def set(obj: Article.type): this.type = {
        useNextArgAs = obj;
        this;
      }

      def to(value: String): Unit = useNextArgAs match {
        case Title => title = value
        case Article => article = value
        case _ => ()
      }

      override def toString: String = f"title: $title; article: $article"
    }



    class BB {
      type arr[T] = collection.mutable.ArrayBuffer[T]
      val x = 0
      //val s = new arr(1)
    }

    class X[T, U] {
      type t = Map[T, U] forSome { type U <: T }
    }

    def doSome[M <: n.Member forSome { val n: Network}](m1: M, m2: M) = (m1, m2)

    class Network {
      class Member
    }

    val b = new BB
    val map = new b.arr[Int](10)


    val book = new Document
    book set Title to "!"
    book set Article to "!!"
    println(book)*/


  /*class Test[+T](val v: T)

  def doSome(name: List[_ <: Person]): Unit = {}

  class Friend[-T] {
    def doSome(name: T): Unit = {
      println("!")
    }
  }

  class Person extends Friend[Person]
  class Student extends Person
  val student = new Student
  val person = new Person

  def makeWithFriend(s: Student, f: Friend[Student]): Unit = f.doSome(s)
  makeWithFriend(student, person)
  */
  /*
    val arr = Array(Array(1, 2, 3))
    val arr2 = Array(Array(2, 3, 4))
    (arr, arr2).zipped.map(_ ++ _).foreach(println)


    val value = "!"
    value match {
      case "!" => true
    }*/

  /*def doSome[T, S](arr: Array[T])(arr2: Array[S]) = arr(0)

  val fString = doSome[String, Int]
  val fInt = fString(Array("!"))
  fInt(Array(1))*/

  /*@deprecated(message="use other method")
  def doSome(@deprecatedName('Name) name: String) = {}

  doSome(Name = "!")*/
  //def doSome[@specialized(Short, Int, Boolean) T](x: T): T = x


  /*val RichFile(path, name, ext) = "/home/cay/readme.txt"
  println(f"$path; $name; $ext")*/
  /*val value = "/home/cay/readme.txt"
  val regex = """([^/]+)/?""".r
  val arr = regex.findAllIn(value)
  //for (v <- regex.findAllIn(value)) println(v)
  //for (regex(x) <- arr) println(x)
  arr.foreach(x => {
    val regex(v) = x; println(v)
  })*/
  //arr.foreach(x => { val v = regex(x); println(v) })

  /*val table = Table()
  println(table())*/

  /*val res = Money(1, 75) + Money(0, 50) == Money(2, 25)
  println(res)*/
  /*val map = scala.collection.mutable.HashMap[String, Money](("first", Money(2, 25)), ("sescond", Money(10, 75)))
  val map2 = scala.collection.mutable.HashMap[String, Money](("sescond", Money(10, 75)), ("first", Money(2, 25)))

  println(map("first") == map2("first"))*/


  /*class Dog(var age: Int, val owner: Int) {
    //def *(that: Dog): Dog = new Dog(count * that.count)
    private val pValue: String = "!pValueSTRING!"
    override def toString: String = "age: %f; owner: $f".format(age, owner)//(f"age: $age; owner: $owner;")

    def +=(that: Dog): Unit = age += that.age
    def update(value: String) = println(f"value: $value; age: $age; owner: $owner;")
    /*def !! = new Dog(99)
    def ?(value: Int): Dog = new Dog(count + value)
    def *(that: Dog): Dog = new Dog(count * that.count)*/

  }
  object Dog {
    def apply(age: Int, owner: Int) = new Dog(age, owner)
    def unapply(that: Dog) = if (that.owner == 0) None else Some(that.age, that.owner)
    //, that.pValue)
  }
  object EDog {
    def unapply(that: Dog) = {
      val Dog(age, owner) = that
      Some(that.age, that.owner, 1)
    }
  }
  object IsPositive {
  //    def unapply(that: Int): Option[Boolean] = if (that > 0) Some(true) else None
    def unapply(value: Double): Boolean = value >= 0
  }
  val d1 = Dog(10, 1)
  val d2 = Dog(2, 0)
  //val d3 = d1 ? 2 * d2
  /*val Dog(age, owner) = d2//, pValue
  println(f"age: $age; owner: $owner;")//value: $pValue;*/


  val dCase = Dog(50, 0)
  val resCase = dCase match {
    case Dog(age, answer) if answer < 0 => "Negative"
    case Dog(age, owner @ IsPositive()) => "Positive"//"age: %s; owner: %s".format(age, owner)
    case _ => "Exception"
  }
  println(resCase)


  case class Currency(val amount: Double, val name: String)
  val cur = Currency(-10.20, "EUR")
  val resCur = cur match {
    case Currency(amount @ IsPositive(), name) => "positive"
    case Currency(amount, "EUR") => "it's EUR"
    case _ => "other"
  }
  println(resCur)
  object Number {
    def unapply(value: String): Option[Int] = {
      try {
        Some(Integer.parseInt(value.trim))
      } catch {
        case ex: NumberFormatException => None
      }
    }
  }
  var Number(n) = "-100"
  n += 10
  println(f"n: $n")

  object Name {
    def unapplySeq(value: String): Option[Seq[String]] = {
      try {
        Some(value.trim.split("\\s+"))
      } catch {
        case ex: Exception => None
      }
    }
  }

  val name = "my1 first job2"
  val resName = name match {
    case Name("my", first, "job") => "!!"
    case Name(my, "first", job) => "!"
    case _ => "?"
  }
  println(resName)
  */

  /*val dr = d1 * d2
  dr += d2
  dr() = "?" + dr.count*/
  //println(dr)

  /*class CreateDB extends Request {
    override def getData(): String = "!"

    def apply(): Unit = {
      other()
    }
  }

  trait ParentTrait {
    def log = "Parent "
  }
  trait Trait1 extends ParentTrait {
    override def log = super.log + "Trait1 "
  }
  trait Trait2 extends ParentTrait {
    override def log = super.log + "Trait2 "
  }*/
  //  val t = new Trait1 with Trait2 { }
  //  println(t.log)
  /*class `while` {
    val `for` = "10"
  }
  val w = new `while`()
  println(w.`for`)
  */
  /*trait Trait {
    val trait_val = 0
    val col = "!"
  }

  trait T {
    val s: String
  }

  trait T2 extends T {
    def s_use = s * 2
  }

  val c2 = new Class with T2 { val s = "??"}// { val s = "**"}
  println(c2.s_use)*/

  /*class Class {
    val class_val = 1
    val col = "!!"
    def class_print = println(class_val)
  }

  class Subclass extends Class {
    override val class_val = 3
    val subclass_val = 2
    def sub_print = println(class_val)
  }*/

  /*class SubclassTrait extends Class with Trait {
    override val col = "!!!"
    val subclassTrait_val = 3
    def subTrait_print = println(col)
  }

  val c = new SubclassTrait().subTrait_print*/


  /*trait Request {
    def getData(): String

    def other(): Unit = println(getData())
  }

  trait MinskRequest extends Request {
    abstract override def getData(): String = super.getData() + " Minsk"
  }

  trait MoscowRequest extends Request {
    abstract override def getData(): String = super.getData() + " Moscow"
  }

  trait BreakRequest extends Request {
    abstract override def getData(): String = super.getData() + " break"
  }

  val request = new CreateDB with MinskRequest with MoscowRequest with BreakRequest
  request()
  */
  //  val pattern = """([a-zA-Z]+)\s+([a-zA-Z]+)""".r
  //val pattern = """([a-zA-Z]+)""".r
  //val pattern(x, y) = "my first"
  //  val str = "my first, our second, they third"
  //  pattern.findAllIn(str).foreach(x => { val pattern(n,m) = x; println(f"$n | $m") })
  //  for (pattern(n, m) <- pattern.findAllIn(str)) println(f"$n | $m")
  //  val iterator = pattern.replaceFirstIn(str, "!")
  //  println(iterator)
  //iterator.foreach(println)
  //println(x + " | " + y)
  //for (pattern(x, y) <- "my face") println(x + " | " + y)
  /*val src1 = scala.io.Source.fromURL("http://inance.ru/")
  val src2 = scala.io.Source.fromString("!")
  val c = scala.io.Source.stdin
  println(src2)

  val out = new java.io.PrintWriter("write.txt")
  for (i <- src1.getLines) out.println(i)
  out.close*/


  /*try {
    val c = 'a'
    val v = c match {
      case '-' => 1
      case '+' => 2
    }
  } catch {
    case e: MatchError => println("MatchError")
    case _ => println("ELSE")
  }
  Character.isDigit('1')
  println(Character.digit('1', 10))*/


  /*import javafx.print.PaperSource

  import _root_.scala.beans.BeanProperty
  import _root_.scala.collection.mutable.ArrayBuffer*/

  /*
  package Person {
    package Man {

      object Examples {
        override def toString(): String = "my class"
      }

      package object Driver {
        var name = "Oleg"
      }

      package Driver {

        class Driver {
          println(name)
        }

        object Driver {
          def apply() = new Driver()
        }

      }

    }

  }

  package Person.Man {

    package Driver {

      class Baker {
        Examples
        _root_.scala.collection.mutable.ArrayBuffer.empty[String]
      }

      object Baker {
        def apply() = new Baker()
      }

    }


  }*/

  /*
  package Person {

    package object Man {
      private var _name = "Oleg"

      def name = _name

      def name_=(newValue: String) = _name = newValue

      def no = {}
    }
    package Man {

      class MyMan {
        println(name)
      }

      object MyMan {
        def apply() = new MyMan
      }

    }

  }


  object Example extends App {
    def iMap(values: List[Int], func: Int => Int) = {
      values.foldRight(List.empty[Int]) { (x: Int, lst: List[Int]) =>
        func(x) :: lst
      }
    }

    /*def func(i: Int): Int = i * 2

    val lst = List(10, 20, 30, 40)
    val res = iMap(lst, func(_))
    println(res)

    var par = (for (i <- (0 until 100).par) yield i)*/

    class A[T] {
      def as(x: Any) = x.asInstanceOf[T]

      def func: String = "A"
    }

    class B extends A {
      override def func: String = super.func + " " + "B"
    }

    class C extends B {
      override def func: String = super.func + " " + "C"
    }

    val c = new C

    c match {
      case o: B => println("!B")
      case o: C => println("!C")
      case _ => println("!_")
    }

    val a = new A[String]

    val d = new C {
      def alert = println("!")
    }
    check(d)

    def check(x: C {def alert: Unit}) = {
      println(x.func)
      x.alert
    }

    abstract class M {
      protected[this] val m: Array[Int]
      protected[this]def func: Unit
    }
    class T extends M{
      protected val m = Array(0, 10)
      protected def func = {}
    }
    val t = new T*/
  //  println(t.v.mkString(", "))
  /*
    class Animal(val id: Int) {

      val arr = new Array[Int](id)
    }

    class Monkey() extends Animal(5) {
      override val id: Int = 20

    }

    val monkey = new Monkey()
    println(monkey.arr.mkString(", "))

  }*/


  /*import _root_.scala.collection.mutable.HashMap
  import _root_.java.util.{ HashMap => JavaHashMap}
  import Person.Man.{name => n, MyMan => m, no => nos}

  val d = Driver
  d()
  println(n)
  m()

  import sys.process._*/

  /*import scala.collection.mutable.ArrayBuffer
  class Person extends Serializable {

    val array = ArrayBuffer[Int](1, 2, 3)
    val map = Map(1 -> "", 2 -> "")
  }

  val in = new java.io.ObjectInputStream(new java.io.FileInputStream("C:/scala/object.obj"))
  val person = in.readObject().asInstanceOf[Person]
  in.close*/
  /*import java.io.File
  def subdirs(dir: File): Iterator[File] = {
    val children = dir.listFiles.filter(_.isDirectory)
    children.toIterator ++ children.flatMap(subdirs _)
  }

  val file = new File("C:/scala")
  val iterator = subdirs(file)
  println(iterator.mkString("\n"))*/

  /*
  object Person {
    private var id = 0
    def createNewId = { this.id += 1; this.id }

  }
  abstract class Person {
    val id: Int = Person.createNewId
    def create: Int
    var ex: Int
    def c {}
  }

  class Employee(override val id: Int) extends Person {
    def create = 0
    var ex = 0
    var e = 1

    override def c: Unit = super.c
  }*/


  /*
    class NewBird(private[NewBird] val name: String,
                  private[this] val weight: Int = 0,
                  @BeanProperty val age: Int = 0,
                  private[NewBird] val other: Boolean = false) {
      if (other) show


      def show = println(this.name + " " + this.weight + " " + this.age)

      def showWith(that: NewBird) = println(this.getClass.getSimpleName + " name: " + this.name + " " + that.name + ";\nage" + this.age + this.age + ";\nweight " + this.weight + ";\nother " + this.other + " " + that.other)
    }

    val newBird = new NewBird("bird", 10, 2, true)
    val newBird2 = new NewBird("bird_2", 20, 11, false)

    newBird.showWith(newBird2)


    class Bird(private[Bird] var name: String) {
      private[this] var weight = 0
      private var age = 0
      private[Bird] var other = false

      def this(name: String, weight: Int) {
        this(name)
        this.weight = weight
        println("auxiliary constructor")
      }

      def this(name: String, weight: Int, age: Int) {
        this(name, weight)
        this.age = age
      }

      def this(name: String, weight: Int, age: Int, other: Boolean) {
        this(name, weight)
        this.age = age
        this.other = other
      }

      def show = println(this.name + " " + this.weight + " " + this.age)

      def showWith(that: Bird) = println("name: " + this.name + " " + that.name + ";\nage" + this.age + this.age + ";\nweight " + this.weight + ";\nother " + this.other + " " + that.other)
    }

    val bird = new Bird("name", 100, 2, true)
    val bird2 = new Bird("also", 50)
    bird.showWith(bird2)

    class Car {

      var wheels = ArrayBuffer[Wheel]()

      class Ex {}

      class Wheel(private[Car] var _pressure: Int, private[this] val radius: Int, private val mark: String) {

        //private[this] def pressure = { println("this"); _pressure }
        //private[this] def _pressure_=(n: Int): Int = { println("this"); _pressure = n }

        def getPressure = _pressure * 10

        def compare(wheel: Wheel) = {
          mark > wheel.mark
        }
      }

      def createWheels(n: Int = 4) = wheels ++= (for (i <- 1 to n) yield new Wheel(i, 10 + i, "mark " + i))

      def printPressure = println(wheels.map(_._pressure).mkString(", "))

      /*def printRadius = println(wheels.map(_.radius).mkString(", "))
      def printMark = println(wheels.map(_.mark).mkString(", "))*/
      def changePressure = wheels(0)._pressure = 10

      def comparePressure(n: Int, m: Int) = wheels(n)._pressure < wheels(m)._pressure

    }*/

  /*val car = new Car
  car.createWheels()
  car.changePressure
  car.printPressure
  println(car.wheels(0).compare(car.wheels(1)))//.comparePressure(0, 1))*/

  /*
    class Person {
      private var pAge: Int = 0

      private def age: Int = pAge
      def age_=(n: Int) = if (n > 5) pAge = n
      def getAge = age
    }

    val p = new Person
    p.age_=(6)
    println(p.getAge)*/
  /*def getIndexes(array: Array[Int], c: Int) = {
    var count = c
    val indexes = for (i <- 0 until array.length if (count > 0 || array(i) >= 0)) yield {
      if (array(i) < 0) count -= 1
      i
    }
    indexes.toArray
  }

  def getArray(array: Array[Int], indexes: Array[Int]) = {
    for (i <- 0 until indexes.length) {
      val iNew = indexes(i)
      array(i) = array(iNew)
    }
    val trimCount = array.length - indexes.length
    val buffer = array.toBuffer
    buffer.trimEnd(trimCount)
    buffer.toArray
  }


  def example(array: Array[Int], count: Int = 2) = {
    val indexes = getIndexes(array, count)
    val resArray = getArray(array, indexes)
    resArray
  }

  val array = (-10 to 10).toArray
  var resArray = example(array)
  printArr(resArray, "resArray")

  def printArr(array: Array[Int], name: String) = {
    print(name + ": ")
    for (i <- array) {
      print(i + " ")
    }
    println
  }*/


  /*
    def wrapper = {
      def example = {
        try {
          throw new IllegalArgumentException("TRY_ARGUMET")
        } catch {
          case exc: IllegalArgumentException => exc.getMessage
        } finally {
          throw new Exception("FINALLY_EXCEPTION")
        }
      }

      try {
        example
      } catch {
        case exc: IllegalArgumentException => "WRAPPER_Illegel_Argument_Exception"
        case exc: Exception => "WRAPPER_Exception"
      }

    }


    def getType[T](v: T)(implicit ev: scala.reflect.ClassTag[T]) = ev.toString

    val x = try {
      1 / 0
    } catch {
      case _: ArithmeticException => "Message"
    }
    try {
      Throw.createException("Exception Message!")
    } catch {
      case _: IllegalArgumentException => println("Illegal Argument Exception")
    }
  }

  object Throw {
    def createException(s: String) = {
      throw new IllegalArgumentException(s)
    }*/
}