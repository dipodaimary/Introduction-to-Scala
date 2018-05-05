class Foo{}
object FooMaker{
  def apply() = new Foo
}
val newFoo = FooMaker()

//
class Bar{
  def apply() = "Hello World"
}

val bar = new Bar

//Objects


class Bar(foo:String)
object Bar{
  def apply(foo:String) = new Bar(foo)
}

//Functions are objects
//Objects extending functions
object addOne extends Function1[Int,Int]{
  def apply(m:Int):Int = m+1
}

//Classes extending Functions
class AddOne extends Function1[Int,Int]{
  def apply(m:Int):Int = m+1
}
//Nice shortcut
class AddOne extends (Int=>Int){
  def apply(m:Int):Int = m+1
}

//Packages
package com.twitter.example

object colorHolder {
  val BLUE = "Blue"
  val RED = "Red"
}
println("the color is: " + com.twitter.example.colorHolder.BLUE)


//Pattern Matching
val times =1
times match{
  case 1 => println("one")
  case 2 => println("two")
  case _ => println("some other number")
}
//Using guards
times match {
  case i if i==1 => println("one")
  case i if i==2 => println("two")
  case _ => println("some other number")
}

//Matching on type
def bigger(o:Any):Any = {
  o match {
    case i:Int if i<0 => i-1
    case i:Int => i+1
    case d:Double if d<0.0 =>d-0.1
    case d:Double =>d+0.1
    case text: String => text+"s"
  }
}

//Matching on class members
def calcType(calc:Calculator) = calc match {
  case _ if calc.brand == "HP" && calc.model == "20B" => "financial"
  case _ if calc.brand == "HP" && calc.model == "48G" => "scientific"
  case _ if calc.brand == "HP" && calc.model == "30B" => "business"
  case _ => "unknown"
}

//Case Classes
case class Calculator(brand:String, model:String)
val hp20b = Calculator("HP", "20b")
val hp20B = Calculator("HP", "20b")
hp20b==hp20B

def calcType(calc:Calculator) = calc match {
  case Calculator("HP", "20B") => "financial"
  case Calculator("HP", "48G") => "scientific"
  case Calculator("HP", "30B") => "business"
  case Calculator(ourBrand, ourModel) => "Calculator: %s %s if of unknown type".format(ourBrand,ourModel)
}

//Exceptions
try{
  remoteCalculatorService.add(1,2)
}catch{
  case e:ServerIsDownException => log.error(e, "the remote calculator service is unavailable. should have kept your trusty HP.")
}finally{
  remoteCalculatorService.close()
}

//Express oriented
val result: Int = try {
  remoteCalculatorService.add(1, 2)
} catch {
  case e: ServerIsDownException => {
    log.error(e, "the remote calculator service is unavailable. should have kept your trusty HP.")
    0
  }
} finally {
  remoteCalculatorService.close()
}
