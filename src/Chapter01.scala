object Chapter01 extends App{
  object Exercise01 {
    val question = "В окне Scala REPL введите 3., затем нажмите клавишу Tab. Какие методы могут быть вызваны?"
    val answer = ()
  }


  object Exercise02 {
    val question = "В окне Scala REPL вычислите квадратный корень из 3, а затем возведите результат в квадрат. Насколько окончательный результат отличается от 3? (Подсказка: переменные res - ваши друзья)"

    def answer(n: Int = 3) = {
      val sqrt = math.sqrt(n)
      val pow = math.pow(sqrt, 2)
      val diff = n - pow
      diff
    }
  }


  object Exercise03 {
    val question = "Переменные val - это значения val или настоящие переменные var?"

    val answer = "val"
  }


  object Exercise04 {
    val question = "Язык Scala может умножать строки на числа - попробуйте выполнить выражение 'crazy' * 3 в REPL. Что получилось в результате? Где в Scaladoc можно найти её описание?"

    def answer(s: String = "crazy", n: Int = 3) = s * n //StringOps
  }


  object Excercise05 {
    val question = "Что означает выражение 10 max 2? В каком классе определён метод max?"

    def answer(n: Int, m: Int) = n.max(m) //Int
  }


  object Excercise06 {
    val question = "Используя число типа BigInt вычислите 2^1024"

    def answer(n: Int = 2, m: Int = 1024) = BigInt(n).pow(m)
  }


  object Excercise07 {
    val question = "Что нужно импортировать для нахождения случайного простого числа вызовом метода probablePrime(100, Random) без использования каких-либо префиксов перед именами ProbablePrime и Random"

    import util.Random
    import BigInt.probablePrime

    def answer(bitLength: Int = 100) = probablePrime(bitLength, Random)
  }


  object Excercise08 {
    val question = "Один из способов создать файл или каталог со случайным именем состоит в том, чтобы сгенерировать случайное число типа BigInt и преобразовать его в систему счисления по основанию 36, в результате получится строка, так как 'qsnvbevtomcj38o06kul'. Отыщите в Scaladocs методы, которые можно было бы использовать для этого."

    import util.Random

    def answer(numbits: Int = 100, radix: Int = 36) = { BigInt(numbits, Random).toString(radix) }
  }


  object Excercise09 {
    val question = "Как получить первый символ строки в языке Scala? А последний символ?"

    def answer(s: String) = (s.head, s.last)
  }


  object Excercise10 {
    val question = "Что делают строковые функции take, drop, takeRight, dropRight? Какие приемущества и недостатки они имеют в сравнении с substring?"

    def answer(s: String, n: Int = 2, count: Int = 4) = {
      val take = s.take(n)
      val takeRight = s.takeRight(n)
      val drop = s.drop(n)
      val dropRight = s.dropRight(n)
      val substring = s.substring(n, count)
    }
  }
}
