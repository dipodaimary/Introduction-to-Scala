def f(s:String) = "f("+s+")"
def g(s:String) = "g("+s+")"

val fComposeG = f _ compose g _
fComposeG("yay")
