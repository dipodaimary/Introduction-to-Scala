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
