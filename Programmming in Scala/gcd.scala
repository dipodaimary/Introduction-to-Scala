def gcd(a:Int, b:Int):Int = {
    if(a>b){
        if(b==0) a else gcd(b,a%b)
    }else{
        if(a==0) b else gcd(a,b%a)
    }
}

def factorial(a:Int) = {
    if(n==0) 1 else n*factorial(n-1)
}