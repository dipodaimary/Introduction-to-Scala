//Expressions
1+1

val two = 1+2

//Variables
var name = "steve"

def addOne(m:Int):Int = {
//Functions
m+1
}

println(addOne(2))

//Anonymous Functions
(x:Int)=>x+1

def timesTwo(i:Int):Int={
println("Hello world")
i*2
}

//Partial application
def adder(m:Int,n:Int) = m+n
val add2 = adder(2,_:Int)
println(add2(3))

//Curried Functions
def multiply(m:Int)(n:Int):Int = m*n
val timesTwo = multiply(2)_
timesTwo(3)

//multiple arguments and curry it

val curriedAdd = (adder _).curried
val addTwo = curriedAdd(2)

//Variable length arguments
def capitalizeAll(args:String*) = {
args.map {arg=> arg.capitalize}
}
/*

*/
//Classes
class Calculator{
val brand:String = "HP"
def add(m:Int,n:Int):Int = m+n
}
//Constructor
class Calculator(brand:String) {
  /*A Constructor*/
  val color:String = if(brand=="TI"){
    "blue"
  }else if(brand=="HP"){
    "black"
  }else{
    "white"
  }
  def add(m:Int,n:Int):Int = m+n
}

//Function vs Methods
class C{
  var acc = 0
  def minc = {acc+=1}
  val finc = {()=>acc+=1}
}

//Inheritance
class ScientificCalculator(brand:String) extends Calculator(brand){
  def log(m:Double,base:Double) = math.log(m)/math.log(base)
}
//Overloading Methods
class EvenMoreScientificCalculator(brand:String) extends ScientificCalculator(brand){
  def log(m:Int):Double =  log(m,math.exp(1))
}

//Abstract Classes
abstract class Shape{
  def getArea():Int //subclass should define this
}
class Circle(r:Int) extends Shape{
  def getArea():Int = {r*r*3}
}
//Traits
trait Car{
  val brand:String
}
trait Shiny{
  val shineRefraction:Int
}
class BMW extends Car{
  val brand = "BMW"
}

class BMW extends Car with Shiny{
  val brand = "BMW"
  val shineRefraction = 12
}

//Types
trait Cache[K, V] {
  def get(key: K): V
  def put(key: K, value: V)
  def delete(key: K)
}
