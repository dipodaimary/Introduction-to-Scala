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

//Functional Combinators
//map
val numbers = List(1,2,3,4,5)
val twice_numbers = numbers.map((i:Int)=>i*2)
//we can also pass functions to map
def twoTimes(i:Int):Int ={
  i*2
}
val twice_numbers = numbers.map(twoTimes)

//foreach
numbers.foreach((i:Int) => i*2) //returns nothing
val doubles = numbers.foreach((i:Int)=>i*2) //doubled: Unit = ()

//filter
val filteredNumbers = numbers.filter((i:Int) => i%2==0)
//similar to map we can pass functions
def isEven(i:Int): Boolean = {
  i%2==0
}
val filteredNumbers = numbers.filter(isEven)


//Zip: zip aggregates the contents of two lists
val list1 = List(1,2,3)
val list2 = List("a","b","c")
val result = list1.zip(list2)

//partition splits a list based on where it falls with respect to the predicate function
val numbers  = List(1,2,3,4,5,6,7,8,9,10)
numbers.partition((i:Int) => i%2==0)
