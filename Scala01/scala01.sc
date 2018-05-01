object LearningScala1 {
  val hello: String = "Bonjour" //immutable
  println(hello)

  //Variable names are mutable
  var hellothere = "Hello "
  hellothere += "There!"
  print(hellothere)

  //Different types of values
  val numberOne: Int = 1
  val truth: Boolean = true
  val letterA: Char = 'a'
  val pi: Double = 3.14159265
  val piSinglePrecision = 3.14159265f
  val bigNumber: Long = 12345678901
  val smallNumber: Byte = 127
  //String printing tricks
  //Concatenating stuffs with +
  println(numberOne + truth + letter + bigNumber + smallNumber)
  //Printf style
  println(f"Pi is about $piSinglePrecision%.3f")
  println(f"Zero padding on the left: $numberOne%05d")
  //Substituting in variables
  println(s"I can use the s prefix to use variables like $numberOne $truth $letterA")
  //substituing expressions with curly braces
  println(s"The s prefix is not limited to variables, I can use to for expressions like: {1+3}")

  //Regular expressions
  val theUltimateAnswer: String = "To life, to universe, and everything is 42."
  val pattern = """.* ([\d]+).*""".r //go find the first integer, wired syntax
  val pattern(answerString) = theUltimateAnswer
  val answer = answerString.toInt
  println(answer) //42

  //Dealing with booleans
  val isGreater = 1 > 2
  val isLesser = 1 < 2
  val impossible = isGreater & isLesser // safety wise better, we sometimes want both the expressions to be evaluated
  val anotherWay = isGreater && isLesser //performance wise better, it will evaluate first and won't bother for the secod if satisied

  val picard: String = "Picard"
  val bestCaptain: String = "Picard"
  val isBest = picard == bestCaptain //true
}