def sum(f : Int=> Int)(a:Int, b:Int):Int = {
    if(a>b) 0 else f(a) + sum(f)(a+1,b)
} 
def cube(x:Int):Int = {
    x*x*x
}

sum(cube)(1,3)