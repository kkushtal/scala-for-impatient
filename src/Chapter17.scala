import _root_.scala.concurrent.{ExecutionContext, Future, Promise}
import _root_.scala.util.{Try, Success, Failure}
import ExecutionContext.Implicits.global
import java.util.concurrent.Executors
import scala.io.StdIn
import _root_.scala.io.Source
import java.net.URL
import java.time.LocalTime

object Chapter17 extends App {

  /* 1. Рассмотрите выражение
   * for (n1 <- Future { Thread.sleep(1000); 2 };
   *      n2 <- Future { Thread.sleep(1000); 40 })
   *   println(n1 + n2)
   * Как это выражение преобразуется в вызовы методов map и flatMap?
   * Как выполняются задания Future, параллельно или последовательно?
   * В каком потоке произойдёт вызов println? */
  object ex1 {
    def f1 = Future {
      Thread.sleep(1 * 1000)
      2
    }

    def f2 = Future {
      Thread.sleep(1 * 1000)
      40
    }

    f1.flatMap(n1 => f2.map(n2 => println(n1 + n2)))
    //val result = f1.flatMap(n1 => f2.map(n2 => n1 + n2))
    //f1.map(n1 => f2.foreach(n2 => println(n1 + n2)))
  }


  /* 2. Напишите функцию doInOrder, принимающую функцию f: T => Future[U] и g: U => Future[V]
   * и возвращающую функцию T => Future[V], которая для заданного t в конечном счёте
   * возвращает g(f(t)) */
  def doInOrder[T, U, V](f: T => Future[U])(g: U => Future[V]): T => Future[V] = {
    f(_) flatMap g
    //x: T => f(x).flatMap(v => g(v))
  }


  /* 3. Повторите предыдущее упражнение
   * для произвольной последовательности функций типа T => Future[T]*/
  def doInOrderSeq[T](seq: (T => Future[T])*): T => Future[T] = {
    seq.reduceLeft((acc, func) => acc(_) flatMap func)
    //seq.reduceLeft((acc, func) => (x => acc(x).flatMap(func)))
  }


  /* 4. Напишите функцию doTogether, принимающую функцию f: T => Future[U] и g: U => Future[V]
   * и возвращающую функцию T => Future[(U, V)], которая выполняет два задания параллельно
   * и для заданного значения t в конечном счёте возвращает (f(t), g(t)) */
  def doTogether[T, U, V](f: T => Future[U])(g: T => Future[V]): T => Future[(U, V)] = {
    x => {
      val fx = f(x)
      val gx = g(x)
      fx zip gx
    }
  }


  /* 5. Напишите функцию, принимающую последовательность объектов Future и возвращающую
   * объект Future, который в конечном счёте возвращает последовательность всех результатов. */
  def traverse[T](seq: Seq[Future[T]]): Future[Seq[T]] = {
    Future.sequence(seq)
  }


  /* 6. Напишите метод Future[T] repeat(action: => T, until: T => Boolean)
   * который асинхронно продолжает вызывать action, пока не получит значения, соответствующего
   * предикату until. Предикат так же должен выполняться асинхронно.
   * Протестируйте метод с функцией, принимающей пароль от стандартного ввода в консоли,
   * и функцией, имитирующей проверку допустимости пароля сравнением его со строкой "secret"
   * после секундной задержки. Подсказка: используйте рекурсию. */
  def repeat[T](action: => T)(until: T => Boolean): Unit = {
    val future = Future[T](action)
    future.filter(until).onComplete {
      case Success(v) => println("Success")
      case Failure(ex) => {
        println("Failure")
        repeat(action)(until)
      }
    }
  }

  def runRepeat(): Unit = {
    //Await.ready(Future(Thread.sleep(60 * 60 * 100)), 1.hour)
    repeat[String](StdIn.readLine("password: "))(x => {
      Thread.sleep(1 * 1000)
      x equals "secret"
    })
  }


  /* 7. Напишите программу, подсчитывающую количество простых чисел между 1 и n с использованием
   * BigInt.isProbablePrime. Разбейте интервал на p частей, где p - количество доступных
   * процессоров (ядер). Подсчитайте количество простых чисел в каждой части с помощью
   * заданий Future, выполняющихся параллельно, и объедините полученные результаты*/
  def countProbablePrime(n: BigInt): Future[BigInt] = {
    val parts = getParts(n)
    val futures = Future.traverse(parts) {
      case (min, max) => Future {
        (min to max).count(_.isProbablePrime(10))
      }
    }
    futures.map(_.sum)
  }

  def getParts(n: BigInt): Seq[(BigInt, BigInt)] = {
    val p = Runtime.getRuntime.availableProcessors()
    val (part, rem) = n /% p

    (1 to p).foldLeft(Seq.empty[(BigInt, BigInt)])((acc, v) => {
      val minmax: (BigInt, BigInt) =
        if (acc.isEmpty)
          (0, rem + part)
        else {
          val lastMax = acc.last._2
          (lastMax + 1, lastMax + part)
        }

      acc :+ minmax
    })
  }


  /* 8. Напишите программу, которая предложит пользователю ввести URL,
   * прочитает веб-страницу с указанным URL и выведет все гиперссылки.
   * Используйте отдельный объект Future для каждого из трёх шагов.*/
  def getURL(path: String = ""): Future[URL] = Future {
    val _path = if (path.nonEmpty) path else StdIn.readLine("URL: ")
    new URL(_path)
  }

  def readFromURL(url: URL): Future[String] = Future(read(url))

  def read(url: URL): String = Source.fromURL(url).mkString

  def getUrls(page: String): Future[Seq[String]] = Future {
    val pattern = """<a.+?\s*href\s*=\s*["\']?([^"\'\s>]+)["\']?"""".r
    pattern.findAllMatchIn(page).map(_.group(1)).toList
    /*map(m => {
      val path = m.group(1)
      val url = new URL(path)
      Try(url)
    }).toList*/
  }

  def getLinks: Future[Seq[String]] = {
    //getUrl flatMap (url => readFromUrl(url) flatMap (page => findAllLinks(page)))
    for {
      url <- getURL();
      page <- readFromURL(url);
      urls <- getUrls(page)
    } yield urls
  }


  /* 9. Напишите программу, которая предложит пользователю ввести URL,
   * прочитает веб-страницу с указанным URL, найдёт все гиперссылки, посетит их параллельно,
   * извлечёт из каждой HTTP-заголовок Server и сообщит частоту встречаемости каждого сервера.
   * Задания, посещающие страницы должны возвращать заголовок */

  def getServerName(url: URL): String = url.openConnection().getHeaderField("Server")

  def toURL(path: String): Try[URL] = Try(new URL(path))

  def getServerNames(urls: Seq[String]): Future[Map[String, Int]] = {
    val serverNames = Future.traverse(urls)(path => Future {
      toURL(path).map(getServerName)
    })

    serverNames.map(_.filter(_.isSuccess)
      .foldLeft(Map.empty[String, Int])((acc, header) => {
        val name = header.get
        val count = acc.getOrElse(name, 0) + 1
        acc + (name -> count)
      }))
  }

  def getServerNames: Future[Map[String, Int]] = {
    //var result = getUrl() flatMap (url => readFromUrl(url) flatMap (page => getLinks(page) flatMap (links => getServerNames(links))))
    for (
      url <- getURL(); //url <- getUrl("https://google.ru/");
      page <- readFromURL(url);
      urls <- getUrls(page);
      serverNames <- getServerNames(urls)
    ) yield serverNames
  }


  /* 10. */


  /* 11. С помощью объектов Future запустите четыре задания, каждое из которых приостанавливается
  * на десять секунд и затем выводит текущее время. Если вы пользуетесь достаточно современным
  * компьютером, весьма вероятно, что он оснащён четыремя процессорами, доступными виртуальной
  * машине Java, и все задания должны завершиться практически одновременно. Теперь попробуйте
  * в качестве контекста выполнения использовать кэширующий пул потоков. Что случилось в этом случае?
  * (Будьте внимательны, определяйте объекты Future после заменеы неявного контекста выполнения.)*/
  def runThreadGlobalPool(count: Int = 4): Unit = {
    val ec = ExecutionContext.Implicits.global
    runThreads(count, ec)
  }

  def runThreadCachedPool(count: Int = 40): Unit = {
    val pool = Executors.newCachedThreadPool()
    val ec = ExecutionContext.fromExecutor(pool)
    runThreads(count, ec)
  }

  def runThreads(count: Int, ec: ExecutionContext): Unit = {
    (1 to count).foreach(v => Future {
      Thread.sleep(10 * 1000)
      println(s"$v. thread: ${LocalTime.now()}")
    }(ec))
  }


  /* 12. Напишите метод, принимающий URL, отыскивающий все гиперссылки, создающий объект Promise
  * для каждой из них, запускающий задание, которое в конечном итоге заполнит все Promise, и
  * возвращающий последовательность Future для объектов Promise. Почему возврат последовательности
  * объектов Promise выглядит менее предпочтительным? */
  def getPagesPromise(urls: Seq[String]): Seq[Future[String]] = {
    val promises = urls.map(url => {
      url -> Promise[String]()
    })

    Future[Unit](promises.map {
      case (path, promise) => {
        val url = Try(new URL(path))
        if (url.isSuccess) {
          val page = read(url.get)
          promise.success(page)
        } else promise.failure(new Exception("Error"))
      }
    })
    promises.map { case (path, promise) => promise.future }
  }

  /*def getAllPagesPromise: Seq[Future[String]] = {
    val result: Seq[Future[String]] = getURL().flatMap(url =>
      readFromURL(url).flatMap(page =>
        getURLs(page).map(urls => getPagesPromise(urls))
      )
    )
    result
  }*/
  /* for (
      url <- getURL();
      page <- readFromURL(url);
      urls <- getURLs(page);
      pages <- getPagesPromise(urls)
    ) yield pages*/


  /* 13. Используйте Promise для отмены задания. Разбейте заданный диапазон больших чисел
  * на несколько цедыъ поддиапазонов и выполните параллельный поиск палиндромных простых чисел.
  * При обнаружении такого числа установите его как значение объекта Future. Все задания должны
  * периодически проверять, завершился ли объект Promise, и завершаться, если это произошло. */
  def countProbablePrimePromise(n: BigInt): Future[BigInt] = {
    val promise = Promise[BigInt]()
    val parts = getParts(n)

    Future.traverse(parts) {
      case (min, max) => Future {
        (min to max).find {
          case v if v % 10 == 0 && promise.isCompleted => true
          case v if v.isProbablePrime(10) => promise.trySuccess(v)
          case _ => false
        }
      }
    }

    promise.future
  }

  /*Future.traverse(parts) {
    case (min, max) => Future {
      (min to max).map(v => {
        if (v % 10 == 0 && promise.isCompleted) false //break Future
        else if (v.isProbablePrime(10)) promise.trySuccess(v)
      })
    }
  }*/


  /*def getURL(url: String = ""): Future[URL] = Future {
    val v = if (url.nonEmpty) url else StdIn.readLine("URL: ")
    new URL(v)
  }*/

  /*def getURLs(page: String): Future[Seq[Try[URL]]] = Future {
    val pattern = """<a.+?\s*href\s*=\s*["\']?([^"\'\s>]+)["\']?"""".r
    pattern.findAllMatchIn(page).
      map(m => {
        val path = m.group(1)
        val url = new URL(path)
        Try(url)
      }).toList
  }*/

  /*def readFromURL(url: URL): Future[String] = Future(read(url))

  def read(url: URL): String = Source.fromURL(url).mkString
*/

  /*def getUrl(url: String = ""): Future[String] = Future {
    if (url.nonEmpty) url
    else StdIn.readLine("URL: ")
  }*/

  /*def readFromUrl(url: String): Future[String] = Future {
    Source.fromURL(url).mkString
  }*/

  /*def getLinks(page: String): Future[Seq[String]] = Future {
    val pattern = """<a.+?\s*href\s*=\s*["\']?([^"\'\s>]+)["\']?"""".r
    pattern.findAllMatchIn(page).map(_.group(1)).toList
  }*/


  /*val futures: Seq[Future[Try[String]]] = paths.map(path => Future {
        toUrl(path).flatMap(getServerName)
      })
      val serverNames = Future.sequence(futures)*/

  /*val future = Future.traverse(links)(path => Future {
    val link = Try(new URL(path))
    if (link.isSuccess) {
      link.get.openConnection().getHeaderField("Server")
    }
    else "!!"
  })
  future.map(_.foldLeft(Map.empty[String, Int]) {
    (acc, header) => acc + (header -> (acc.getOrElse(header, 0) + 1))
  })*/

  //val f = getLinks("https://www.google.ru/")


  //var result = getUrl(Url) flatMap (url => readFromUrl(url) flatMap (page => findAllLinks(page) flatMap (links => execLinks(links))))
  /*val Url = "https://www.google.ru/"
  val r = for (
    url <- getUrl(Url);
    page <- readFromUrl(url);
    links <- getLinks(page);
    headers <- getServerNames(links)
  ) yield headers

  val page = Source.fromURL(Url).mkString
  val pattern = """<a.+?\s*href\s*=\s*["\']?([^"\'\s>]+)["\']?"""".r
  val r2 = pattern.findAllMatchIn(page).map(_.group(1)).toList*/
  //val r2 = pattern.findAllIn(source).toList


}
