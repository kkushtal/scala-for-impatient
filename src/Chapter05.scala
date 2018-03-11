object Chapter05 {

  def main(args: Array[String]): Unit = {
    val ss, acc = Account()
    println(ss.id + " " + acc.id)
  }

  class Account private(val id: Int, initialBalance: Double = 1.0) {
    private[this] var balance = initialBalance

    override def toString(): String = this.balance.toString
  }

  object Account {
    private[this] var lastNumber = 0

    def apply(): Account = {
      new Account(newUniqueNumber())
    }

    private[this] def newUniqueNumber() = {
      lastNumber += 1;
      lastNumber
    }
  }

  /* 1. Усовершенствуйте класс Counter в разделе 5.1 "Простые классы и методы без параметров",
   * так чтобы значение счётчика не превращалось в отрицательное число по достижении Int.MaxValue */
  class Counter(private var value: Int = 0) {
    def increment(): Unit = {
      if (value < Int.MaxValue) value += 1 else throw new Exception("current value > Int.MaxValue")
    }

    def current = value
  }


  /* 2. Напишите класс BankAccount с методами deposit и withdraw
   * и свойством balance, доступным только для чтения. */
  class BankAccount {
    private var total = 0.0

    def balance = total

    def deposit(amount: Double): Unit = total += amount

    def withdraw(amount: Double): Unit = total -= amount

  }


  /* 3. Напишите класс Time со свойствами hours и minutes, доступными только для чтения, и методом
   * before(other: Time): Boolean, который проверяет, предшествует ли время this времени other.
   * Объект Time должен конструироваться как new Time(hrs, min), где hrs - время в 24-часовом формате. */
  class Time(val hours: Int, val minutes: Int) {
    check(this)

    def check(time: Time): Unit = {
      if (!(0 until 24).contains(time.hours)) throw new Exception("hours should be from 0 until 24")
      if (!(0 until 60).contains(time.minutes)) throw new Exception("minutes should be from 0 until 60")
    }

    def getMinutes = hours * 60 + minutes

    def before(other: Time): Boolean = {
      check(other)
      other match {
        case time if hours < time.hours => true
        case time if hours == time.hours && minutes < time.minutes => true
        case _ => false
      }
    }
  }


  /* 4. Перепишите класс time из предыдущего упражнения так, чтобы внутри было представлено количество минут,
   * прошедших с начала суток (между 0 и 24 * 60 - 1). Общедоступный интерфейс при этом не должен измениться.
   * То есть эти изменения не должны оказывать влияние на клиентский код. */
  class TimeX(override val hours: Int, override val minutes: Int) extends Time(hours, minutes) {
    private val minutesCount = hours * 60 + minutes

    def before(other: TimeX): Boolean = {
      check(other)
      this.minutesCount < other.minutesCount
    }
  }


  /* 5. Создайте класс Student со свойствами в формате JavaBeans name (тип String) и id (тип Long),
   * доступными для чтения/записи. Какие методы будут сгенерированы?
   * Сможете ли вы вызвать методы доступа в формате JavaBeans из программного кода Scala? Необходимо ли это? */

  import scala.beans.BeanProperty

  class Student(@BeanProperty var name: String, @BeanProperty var id: Long) {
    def check = {
      this.getName
      this.setName("New Name")
      this.getId
      this.setId(10)
    }
  }


  /* 6. В классе Person из раздела 5.2 "Свойства с методами доступа"
   * реализуйте главный конструктор, преобразующий отрицательное значение возрваста в 0*/
  class Person(_age: Int) {
    private var privateAge = if (_age < 0) 0 else _age

    def age = privateAge

    def age_=(newValue: Int): Unit = if (newValue > privateAge) privateAge = newValue
  }


  /* 7. Напишите класс Person с главным конструктором, принимающим строку, которая содержит имя, пробел и фамилию,
   * например: new Person("Fred Smith"). Сделайте свойства firstname и lastName доступными только для чтения.
   * Должен ли параметр главного конструктора объявляться как var, val или как обычный параметр? Почему?
   * */
  class PersonName(fullName: String) {
    val Array(firstName, lastName) = fullName.split("\\s+")
  }


  /* 8. Создайте класс Car со свойствами, определяющими производителя, название модели и год производства,
   * которые доступны только для чтения, и свойство с регистрационным номером автомобиля, доступное для чтения/запис.
   * Добавьте четыре конструктора. Все они должны принимать название производителя и название модели.
   * При необходимости в вызове конструктора могут также указываться год и регистрационный номер.
   * Если год не указан, он должен устанавливаться равным -1, а при отсутствии регистрационного номера
   * должна устанавливаться пустая строка. Какой констркутор вы выберете в качестве главного? Почему?
   */
  class Car(val manufacturer: String, val mame: String) {
    var licence: String = _
    private var _year: Int = _

    def year = this._year

    def this(manufacturer: String, name: String, year: Int) {
      this(manufacturer, name)
      this._year = year
    }

    def this(manufacturer: String, name: String, license: String) {
      this(manufacturer, name)
      this.licence = license
    }

    def this(manufacturer: String, modelName: String, license: String = "", year: Int = -1) {
      this(manufacturer, modelName, license)
      this._year = year
    }
  }


  /* 9. Повторно реализуйте класс из предыдущего упражнения на языке Java, C# или C++ (по выбору).
   * Насколько короче получился класс на языке Scala?
   */


  /* 10. Взгляните на следующее описание класса:
   * class Employee(val name: String, var salary: Double) {
   *   def this() { this("John Q. Public", 0.0) }
   * }
   * Перепишите его так, чтобы он содержал явные определения полей и имел главный конструктор по умолчанию.
   * Какое объявление вам нравится больше? Почему?
   */
  class Employee(val name: String = "John Q. Public", val salary: Double = 0.0)

}
