import scala.reflect.internal.JavaAccFlags

/* 1. Напишите программу, на примере которой можно было бы убедиться, что
 * package com.horstmann.impatient
 * тоже самое, что и
 * package com
 * package horstmann
 * package impatient */

/* package com.horstmann.impatient {
 *   object Test {
 *     def apply() = println("Test completed successfully")
 *   }
 * }
 *
 * package com
 * package horstmann
 * package impatient
 * impatient.Test()*/


/* 2. Напишите головоломку, которая смогла бы сбить с толку ваших коллег, программистов на Scala,
 * использующую пакет com, не являющийся пакетом верхнего уровня. */
package scala.collection {
  package com {
    // Some Code
    package mutable {
      // Some Code
    }

  }

}


/* 3. Напишите пакет random с функциями nextInt(): Int, nextDouble(): Double и setSeed(seed: Int): Unit.
 * Дл я генерации случайных чисел используйте линейный конгуэртный генератор
 * next = previous * a + b mod 2^n, где a = 1664525, b = 1013904223, n = 32 */
package object random {
  private val a = 1664525
  private val b = 1013904223
  private val n = 32
  private var seed = 0.0

  private def getNext() = {
    seed = seed * a + b % math.pow(2, n)
    math.abs(seed)
  }

  def nextInt(): Int = getNext().toInt

  def nextDouble(): Double = getNext().toDouble

  def setSeed(seed: Int): Unit = this.seed = seed
}


/* 4. Как вы думаете, почему создатели языка Scala реализовали синтаксис объектов пакетов,
 * вместо того чтобы просто разрешить добавлять функции и переменные в пакет? */


/* 5. Что означает определение private[com] def Raise(rate: Double)?
 * Есть ли в этом смысл? */


object Chapter07 extends App {
  /* 6. Напишите программу, копирующую все элементы из Java-хеша в Scala-хеш.
   * Используйте операцию импортирования для переименования обоих классов. */

  import scala.collection.JavaConverters._
  import java.util.{HashMap => JMap}
  import scala.collection.mutable.{Map => SMap}

  def JavaToScalaHash[A, B](jMap: JMap[A, B]) = SMap.empty ++ jMap.asScala


  /* 7. В предыдущем упражнении перенесите все инструкции import
   * в самую внутреннюю область видимости, насколько это возможно. */

  import java.util.{HashMap => JMap}

  def JavaToScalaHashX[A, B](jMap: JMap[A, B]) = {
    import scala.collection.JavaConverters._
    import scala.collection.mutable.{Map => SMap}
    SMap.empty ++ jMap.asScala
  }


  /* 8. Опишите эффект следующих инстраукций:
   * import java._
   * import javax._
   * Насколько правильно это решение? */


  /* 9. Напишите программу, импортирующую класс java.lang.Ssytem, читающую имя пользователя
   * из системного свойства user.name, пароль из объекта Console и выводящую сообщение
   * в стандартный поток ошибок, если пароль недостаточно "секретный".
   * В противном случае программа должна вывести приветствие в стандартный поток вывода.
   * Не импортируйте ничего другого и не используйте квалифицированные имена (с точками). */

  def login() = {
    import java.lang.System._

    val name = getProperty("user.name")
    val password = readLine("Password: ") //scala.io.StdIn.readLine()
    if (password == "secret") out.println("Hi, " + name)
    else err.println("Not secret")
  }

  /* 10. Помимо StringBuilder, какие другие члены пакета java.lang переопределяет пакет scala? */
  /* Boolean, Byte, Double, Float, Long, Short */

}
