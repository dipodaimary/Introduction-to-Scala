def f(s:String) = "f("+s+")"
def g(s:String) = "g("+s+")"

//Composed function
val fComposed = f _ compose g _
#AndThen
val fAndThenG = f _ andThen g _

//Currying vs Partial Functions
val one: PartialFunction[Int, String] = {case 1 => "one"}
one.isDefinedAt(1)
one.isDefinedAt(2)

val two : PartialFunction[Int,String] = {case 2 => "two"}
val three: PartialFunction[Int, String] = {case 3 => "three"}
val wildCard: PartialFunction[Int,String] = {case _ => "something else"}
val partial = one orElse two orElse three orElse wildCard

//The mystery of case
case class PhoneExt(name: String, ext: Int)
val extensions = List(PhoneExt("steve",100),PhoneExt("robey",200))
extensions.filter{case PhoneExt(name,extension)=> extension<200}