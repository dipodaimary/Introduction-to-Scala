object rationals{
    val x = new Rational(1,2)
    println(x.numer)
    println(y.denom)
}

class Rational(x:Int, y:Int){
    def numer = x
    def denom = y
    def add(that: Rational) = new Rational(numer*that.denom + that.numer*denom,denom*that*denom)
    def neg :Rational = new Rational(-numer,denom)
    def sub(that:Rational) = add(that.neg)
    override def toString = numer +"/" + denom
}