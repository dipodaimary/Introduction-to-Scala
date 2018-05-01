object DataStructures {
  //Datastructures

  //Tuples-immutable lists, database fields, or columns
  //Useful for passing around the rows of data
  val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")
  println(captainStuff)
  //Accessing individual items
  println(captainStuff._1)
  print(captainStuff._3)

  //We can create a key-value pair
  val picardsShip = "Picard" -> "Enterprise-D"
  println(picardsShip._1)
  println(picardsShip._2)

  //We can mix different types in a tuple
  val aBunchOfStuff = ("Kirk", 1964, true)

  /*Lists, collection of objects mutable,
  singly linked list under the hood
  */
  val shipList = List("Enterprise", "Defiant", "Voyager", "Deep Space Nine")
  println(shipList(1))
  //head tail

  println(shipList.head) //Enterprise
  println(shipList.tail)

  //Iterating through list
  for (ship <- shipList) {
    println(ship)
  }

  //use map to apply elemeent wise function
  val backwardShips = shipList.map((ship: String) => {
    ship.reverse
  })

  for (ship <- shipList) {
    println(ship)
  }

  //reduce
  val numberList = List(1, 2, 3, 4, 5)
  val sum = numberList.reduce((x: Int, y: Int) => x + y)
  println(sum)

  //filter() to remove stuffs that we don't want
  val hateFives = numberList.filter((x: Int) => x != 5)
  val hateThrees = numberList.filter(_ != 3)

  val moreNumbers = List(6, 7, 8)
  val lotsOfNumbers = numberList ++ moreNumbers

  //More list functions
  val reversed = lotsOfNumbers.reverse
  val sorted = lotsOfNumbers.sorted
  val lotsOfDuplicates = lotsOfNumbers ++ numberList
  val distinctValues = lotsOfDuplicates.distinct
  val maxValue = numberList.max
  val total = lotsOfNumbers.sum
  val hasThree = lotsOfNumbers.contains(3)


  //Maps some other languages a dictionary
  val shipMap = Map("Kirk" -> "Enterprise", "Picard" -> "Enterprise-D", "Sisko" -> "Deep Space Nine", "Janeway" -> "Voyager")
  println(shipMap("Picard"))

  //Dealing with missing keys
  println(shipMap.contains("Archer"))
  val archersShip = util.Try(shipMap("Archer")) getOrElse ("Unknown")
}