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