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

//Find
numbers.find((i:Int)=>i>5) //alternatively numbers.find(_>5)

//drop and dropWhile
val droppedNumbers = numbers.drop(5) //drops first i the elements
val droppedNumbers = numbers.dropWhile(_<3) //drop numbers with respect to the predicate function

//foldLeft
val temp  = numbers.foldLeft(0)((m:Int,n:Int)=>m+n)
//visualize
numbers.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
numbers.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }

//Flatten: flatten collapses one level of nested structure
List(List(1,2),List(3,4)).flatten
List(List(List(0,1),List(1,2)),List(3,4)).flatten

//Flat Map
val nestedNumbers = List(List(1, 2), List(3, 4))
nestedNumbers.flatMap(x => x.map(_ * 2)) //short hand for nestedNumbers.map((x: List[Int]) => x.map(_ * 2)).flatten


//Generalized Functional Combinators
def ourMap(numbers:List[Int],fn:Int=>Int):List[Int] = {
  numbers.foldRight(List[Int]()){
    (x:Int,xs:List[Int])=>fn(x)::xs
  }
}
def timesTwo(i:Int)={i*2}
ourMap(numbers, timesTwo(_))
