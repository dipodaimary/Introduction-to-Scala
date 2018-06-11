def sum(f : Int=> Int)(a:Int, b:Int):Int = {
    if(a>b) 0 else f(a) + sum(f)(a+1,b)
} 
def cube(x:Int):Int = {
    x*x*x
}

sum(cube)(1,3)

//Exercise

object Exercise{
    def product(f: Int=>Int)(a:Int, b:Int):Int = {
        if(a>b) 1 else f(a)*product(f)(a+1,b)
    }
}
Exercise.product(x=>x*x)(3,4)

//Map Reduce version
object Exercise{
    def mapReduce(f:Int=>Int, combine:(Int,Int)=>Int, zero:Int)(a:Int, b:Int):Int = {
        if(a>b) zero
        else combine(f(a), mapReduce(f, combine, zero)(a+1, b))
    }
    def product(f:Int => Int)(a:Int, b:Int):Int = mapReduce(f, (x,y)=>x*y,1)(a,b)
}
Exercise.product(x=>x*x)(3,4)