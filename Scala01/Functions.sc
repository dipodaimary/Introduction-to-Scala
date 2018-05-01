object LearningScala03 {
  //Functions
  //Format is def <function name>(<parameter name>:type..):<return type> = {expression}
  def square(x: Int): Int = {
    x * x
  }

  println(square(4))

  def cubeIt(x: Int): Int = {
    x * x * x
  }

  println(cubeIt(3))

  //functions can take other functions as parameters
  def transformFromInt(x: Int, f: Int => Int): Int = {
    f(x)
  }

  val result = transformFromInt(2, cubeIt)

  //Lambda functions, anonymous functions, function literals
  transformFromInt(2, x => x * x * x)
  transformFromInt(10, x => x / 2)
  transformFromInt(2, x => {
    val y = x * 2;
    y * y
  })
  //This is really important
}