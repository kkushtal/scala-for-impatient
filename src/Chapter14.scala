import java.io.{File, IOException}
import _root_.scala.reflect.ClassTag

object Chapter14 extends App {
  /* 1. */
  /*val file = new File("C:\\scala\\first\\src")

  def getFiles(file: File): Array[File] = {
    val all = file.listFiles()
    val (files, directories) = all.partition(_.isFile)
    files ++ directories.flatMap(getFiles)
  }*/


  /* 2. Используя сопоставление с образцом, напишите функцию swap, которая принимает
   * пару целых чисел и возвращает ту же пару, поменяв компоненты местами. */
  def swap2[T, R](pair: (T, R)): (R, T) = pair match {
    case (f, s) => (s, f)
  }


  /* 3. Используя сопоставление с образцом напишите функцию swap, которая меняет местами
   * первые два элемента массива, если он имеет длину не меньше двух */
  def swap3[T: ClassTag](input: Array[T]): Array[T] = input match {
    case Array(first, second, rest@_*) => Array(second, first) ++ rest
    case _ => input
  }


  /* 4. Добавьте case-класс Multiple, налсдеюущий класс Item. Например,
   * Multiple(10, Article("Backwell Toaster", 29.95)) описывает десять тостеров.
   * Разумеется, должна предусматриваться возможность обрабатывать любые элементы,
   * такие как пакет или множитель во втором аргументе. Расширьте функцию price,
   * чтобы она могла обрабаывать этот новый класс. */
  abstract class Item

  case class Article(description: String, price: Double) extends Item

  case class Bundle(description: String, discount: Double, items: Item*) extends Item

  case class Multiple(amount: Int, items: Item*) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its@_*) => its.map(price).sum - disc
    case Multiple(amount, its@_*) => its.map(price).sum * amount
    case _ => 0
  }


  /* 5. Для представления деревьев, хранящих значения только в листьях можно использовать списки.
   * Например, список ((3 8) 2 (5)) описывает дерево:
   *     *
   *    /|\
   *   * 2 *
   *  /\   |
   * 3 8   5
   * В этос случае одни элементы списка будут числами, а другие - списками. Однако в Scala
   * нельзя создавать разнородные списки, поэтому придётся использовать List[Any]. Напишите
   * функцию leafSum для вычисления суммы всех значений листьев, используя сопоставление с образцом
   * для отделения чисел от списков.
   * */
  val lst: List[Any] = List(List(3, 8), 2, List(5))

  //tree.foldLeft(0d)((acc, node) => acc + nodeMatch(node))
  def leafSum(tree: List[Any]): Double = tree.foldLeft(0d)(_ + nodeMatch(_))

  def nodeMatch(item: Any): Double = item match {
    case n: Number => n.doubleValue()
    case lst: List[_] => leafSum(lst)
    case _ => 0
  }


  /* 6. Такие деревья лучше всего моделировать с применением case-классов.
   * Начните с бинарных деревьев.*/
  val tree = Node(Node(Leaf(3), Leaf(8)), Leaf(2))

  sealed abstract class BinaryTree

  case class Leaf(value: Int) extends BinaryTree

  case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree

  /* Напишите функцию, вычисляющую сумму всех значений листьев. */
  def leafSumTree(tree: BinaryTree): Int = tree match {
    case Leaf(v) => v
    case left Node right => leafSumTree(left) + leafSumTree(right)
    case _ => 0
  }


  /* 7. Расширьте дерево из предыдущего упражнения, чтобы каждый узел в нём мог иметь
   * произвольное количество дочерних узлов, и перепишите функцию leafSum. Дерево
   * в упражнении 5 должно выражаться, как */
  val treeM = NodeM(NodeM(Leaf(3), Leaf(8)), Leaf(2), NodeM(Leaf(5)))

  case class NodeM(items: BinaryTree*) extends BinaryTree

  def leafSumTreeM(tree: BinaryTree): Int = tree match {
    case Leaf(v) => v
    case NodeM(its@_*) => its.foldLeft(0)(_ + leafSumTreeM(_))
    case _ => 0
  }


  /* 8. Расширьте дерево из предыдущего управжнения, чтобы каждый узел, неявляющийся листом,
   * вдобавок к дочерним узлам мог хранить оператор. Затем напишите функцию eval,
   * вычисляющую значение. Например, дерево
   *     +
   *    /|\
   *   * 2 -
   *  /\   |
   * 3 8   5
   * имеет значение (3 x 8) + 2 + (-5) = 21 */
  val treeOp = NodeOp(Op.Plus, NodeOp(Op.Product, Leaf(3), Leaf(8)), Leaf(2), NodeOp(Op.Minus, Leaf(5)))

  case class NodeOp(op: Op, items: BinaryTree*) extends BinaryTree

  class Op(val init: Int, func: (Int, Int) => Int) {
    def apply(a: Int, b: Int) = func(a, b)
  }

  object Op {
    val Minus = new Op(0, _ - _)
    val Plus = new Op(0, _ + _)
    val Product = new Op(1, _ * _)
  }

  def eval(tree: BinaryTree): Int = tree match {
    case Leaf(v) => v
    case NodeOp(op, its@_*) => its.foldLeft(op.init)((acc, node) => op(acc, eval(node)))
    case _ => 0
  }


  /* 9. Напишите функцию, вычисляющую сумму всех непустых значений в List[Option[Int]].
   * Не используйте выражение match*/
  def sum(lst: List[Option[Int]]): Int = {
    var sum = lst.map {
      case Some(v) => v
      case None => 0
    }.sum
    sum = lst.collect { case Some(v) => v }.sum
    sum = lst.foldLeft(0)(_ + _.getOrElse(0))
    sum = lst.flatten.sum
    sum
  }


  /* 10. Напишите функцию, получающую две функции типа Double => Option[Double] и
   * конструирующую на их основе третью функцию того же типа. Новая функция должна
   * возвращать None, если любая из двух исходных функций вернёт это значение.
   * Например: Вызов h(2) должен вернуть Some(1), а вызов h(1) и h(0) должен вернуть None. */
  def f(x: Double): Option[Double] = if (x != 1) Some(1 / (x - 1)) else None

  def g(x: Double): Option[Double] = if (x >= 0) Some(math.sqrt(x)) else None

  def h = compose(f, g)

  def compose(f: Double => Option[Double], g: Double => Option[Double]): Double => Option[Double] = {
    //f(_).flatMap(x => g(x))
    (x: Double) =>
      f(x) match {
        case Some(v) => g(v)
        case _ => None
      }
  }

}