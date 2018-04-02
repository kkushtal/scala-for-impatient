import _root_.scala.collection.mutable

object Chapter13 extends App {
  /* 1. */
  def indexes(input: String): mutable.Map[Char, mutable.SortedSet[Int]] = {
    (mutable.Map.empty[Char, mutable.SortedSet[Int]] /: input.zipWithIndex) (
      (map, el) => {
        val chr = el._1
        val indx = el._2
        map.getOrElseUpdate(chr, mutable.SortedSet.empty[Int])
        map(chr) += indx
        map
      })
  }

  /* 2. */
  def indexesX(input: String): Map[Char, List[Int]] = {
    (Map.empty[Char, List[Int]] /: input.zipWithIndex) (
      (map, el) => {
        val chr = el._1
        val indx = el._2
        val list = map.getOrElse(chr, Nil) :+ indx
        map + (chr -> list)
        //val list = map.getOrElse(char, List.empty[Int]) :+ index
        //map.updated(char, list)
      })
  }

  /* 3. */
  val list = List(0, 1, 2, 3, 4, 5, 6, 7)
  def delZero(lst: List[Int]): List[Int] = lst.filter( _ != 0)
  /*val list = List(0, 1, 2, 3, 4, 5, 6, 7)

  def delZero(input: List[Int]): List[Int] = {
    (List.empty[Int] /: input) (
      (list, el) => {
        if (el == 0) list
        else list :+ el
      })
  }

  delZero(list)*/
  /* 4. */
  //val arr = Array("Tom", "Fred", "Harry")
  //val map = Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)
  def four(arr: Array[String], map: Map[String, Int]): Array[Int] = arr.flatMap(map.get)

  /* 5. */
  def mkStringX(input: Array[String], start: String, sep: String, end: String): String = {
    val output = input.reduceLeft(_ + sep + _)
    start + output + end
  }

  /* 6. */
  def six_1(lst: List[Int]): List[Int] = (lst :\ List.empty[Int]) (_ :: _)

  def six_2(lst: List[Int]): List[Int] = (List.empty[Int] /: lst) (_ :+ _)

  def six_rev(lst: List[Int]): List[Int] = (List.empty[Int] /: lst) ((res, el) => el +: res)

  /* 7. */
  //val prices = List(1.0, 2.0, 3.0)
  //val quantities = List(10, 20, 30)
  def multiply(prices: Iterable[Double], quantities: Iterable[Int]): Iterable[Double] = {
    (prices zip quantities).map(Function.tupled(_ * _))
  }

  /* 8. */
  val arr = Array(1, 2, 3, 4, 5, 6)

  def group(input: Array[Int], n: Int): Array[Array[Int]] = input.grouped(n).toArray

  /* 9. */
  /* 10. */
  /*val input = "My new string"
  input.par.aggregate(mutable.HashMap.empty[Char, Int])(
    (map, char) => {
      map(char) = map.getOrElse(char, 0) + 1
      map
    }, (map, map2) => (
      map ++ map2
    ))
  val frequencies = mutable.HashMap[Char, Int]*/


}


/*input.zipWithIndex.foldLeft(mutable.Map.empty[Char, mutable.SortedSet[Int]])*/
