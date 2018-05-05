//Arrays
val numbers = Array(1,2,3,4,5,1,2,3,4,5)
//List
val numbers = List(1,2,3,4,5,1,2,3,4,5)
//Set
val numbers = Set(1,2,3,4,5,1,2,3,4,5)
//Tuple 1 based rather than 0 based
val hostPort = ("localhost",80)
hostPort._1
hostPort match {
  case ("localhost",port) => println("yes")
  case (host,port) => println("no")
}
//Tuple has some special sauce for simply making Tuples of 2 values: ->
1->2
//Maps
Map(1->2)
Map("foo"->"bar")

//Options
//Basic interface for Option looks like
trait Option[T]{
  def isDefined: Boolean
  def get: T
  def getOrElse(t:T): T
}

val numbers = Map("One"->1,"Two"->2)
numbers.get("One")
numbers.get("Two")

val result = if (numbers.get("Two").isDefined) {
  numbers.get("Two").get * 2
} else {
  0
}

val result = res1 match {
  case Some(n) => n * 2
  case None => 0
}
