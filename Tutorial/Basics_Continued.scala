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
object addOne extends Function1[Int,Int]{
  def apply(m:Int):Int = m+1
}
