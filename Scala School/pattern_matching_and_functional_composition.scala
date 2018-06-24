def f(s:String) = "f("+s+")"
def g(s:String) = "g("+s+")"

val fComposeG = f _ compose g _
fComposeG("yay")

val fAndThenG = f _ andThen g _
fAndThenG("yay")
