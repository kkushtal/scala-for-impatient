object Chapter02 extends App {

  object Exercise01 {
    val question = "Сигнум числа равен 1, если число положительное, -1 - если отрицательное, и 0 - если оно равно нулю. Напишите функцию, вычисляющую это значение"

    def answer(x: Int) = if (x > 0) 1 else if (x < 0) -1 else 0
  }


  object Exercise02 {
    val question = "Какое значение возвращает пустой блок {}? Каков его тип?"

    val answer: Unit = ()
  }


  object Exercise03 {
    val question = "Придумайте ситуацию, когда присвоение x = y = 1 будет допустимым в Scala. (Подсказка: выберите подходящий тип для x)"

    def answer = {
      var y: Int = 0
      var x: Unit = ()
      x = y = 1
    }
  }


  object Exercise04 {
    val question = "Напишите на языке Scala цикл, эквивалентный циклу на языке Java: for (int i = 10; i >= 0; i--) System.out.println(i);"

    def answer(n: Int = 10) = for (i <- n to(0, -1)) println(i)
  }


  object Exercise05 {
    val question = "Напишите процедуру, которая выводит числа от n до 0"

    def answer(n: Int = 10) { for (i <- n to(0, -1)) print(i + " ") }
  }


  object Exercise06 {
    val question = "Напишите цикл for для вычисления произведения кодовых пунктов Unicode для всех букв в строке. Например, произведение символов в строке 'Hello' равно 9415087488L"

    def answer(s: String) = {
      var product = 1
      for (i <- s) product *= i
      product
    }
  }


  object Exercise07 {
    val question = "Решите предыдущее управжнение без применения цикла. (Подсказка: загляните в описание класса StringOps в Scaladoc)"

    def answer(s: String) = s.map(_.toInt).product
  }


  object Exercise08 {
    val question = "Напишите функцию product(s: String), вычисляющую произведение, как описано в предыдущих упражнениях."

    def product(s: String) = s.map(_.toInt).product
  }


  object Exercise09 {
    val question = "Сделайте функцию из предыдущего упражнения рекурсивной."

    def product(s: String): BigInt = if (s.isEmpty) 1 else s.head * product(s.tail)
  }


  object Exercise10 {
    val question = "Напишите функцию, вычисляющую x^n, где n - целое число. Используйте следующее рекурсивное определение:" +
      "x^n = y^2, если n - чётное и положительное число, где y = x^(n/2)" +
      "x^n = x * x^(n-1), если n - нечётное и положительное число " +
      "x^0 = 1" +
      "x^n = 1/x^(-n), если n - отрицательное число" +
      "Не используйте функцию return"

    def pow(x: Int, n: Int): Double = {
      if (n == 0) 1
      else if (n < 0) 1 / math.pow(x, -n)
      else if (n % 2 == 0) {
        val y = math.pow(x, n / 2);
        math.pow(y, 2)
      }
      else x * pow(x, n - 1)
    }
  }

}

