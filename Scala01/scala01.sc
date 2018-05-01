object LearningScala1 {
  val hello: String = "Bonjour" //immutable
  println(hello)

  //Variable names are mutable
  var hellothere = "Hello "
  hellothere += "There!"
  print(hellothere)
}