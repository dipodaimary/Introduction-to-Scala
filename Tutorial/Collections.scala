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
