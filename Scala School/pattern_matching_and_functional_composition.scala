def f(s:String) = "f("+s+")"
def g(s:String) = "g("+s+")"

val fComposeG = f _ compose g _
fComposeG("yay")

val fAndThenG = f _ andThen g _
fAndThenG("yay")


//Currying vs Partial Functions
val one: PartialFunction[Int,String] = {case 1 => "one"}
one.isDefinedAt(1)
one.isDefinedAt(2)
one(1)
val two: PartialFunction[Int,String] = {case 2 => "two"}
two.isDefinedAt(1)
two.isDefinedAt(2)
two(2)

val three: PartialFunction[Int,String] = {case 3 => "two"}

val wildcard: PartialFunction[Int, String] = {case _ => "Something Else"}
wildcard(3)

val partial = one orElse two orElse three orElse wildcard
partial(1)
partial(2)
partial(3)
partial(4)
