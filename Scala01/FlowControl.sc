object LearningScala2 {
  //Flow control
  //ifelse
  if (1 > 3) println("Impossible") else println("The world makes sense")
  if (1 > 3) {
    println("Impossible!")
  } else {
    println("The world makes sense.")
  }

  //Matching like switch in other languages
  val number = 3
  number match {
    case 1 => println("One")
    case 2 => println("Two")
    case 3 => println("Three")
    case _ => println("Something else")
  }

  //For loops
  for (x <- 1 to 4) {
    val squared = x * x
    println(squared)
  }

  //while loops
  var x = 10
  while (x >= 0) {
    println(x)
    x -= 1
  }

  //Do while loops
  x = 0
  do {
    println(x);
    x += 1
  } while (x <= 10)

  //Expressions
  //"Returns" the final value in the block automatically
  {
    val x = 10;
    x += 20
  }
  println({
    val x = 10; x + 20
  })
}