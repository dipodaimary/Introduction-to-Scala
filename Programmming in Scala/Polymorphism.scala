trait List[T]{
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
}
class Cons[T](val head:T, val tail:List[T]) extends List[T]{
    def isEmpty = false
}
class Nil[T] extends List[T]{
    def isEmpty = true
    def head:Nothing = throw NoSuchElementException("Nil.head")
    def tail:Nothing = throw NoSuchElementException("Nil.tail")
}

//nth
object nth{
    def nth(n:Int, xs:List[T]):T = {
        if (xs.isEmpty) throw new IndexOutOfBoundsException
        if(n == 0) xs.head
        else nth(n-1,xs.tail) //nth: [T](n: Int, xs:week4.List[T]) T
    }
    val list = new Cons(1,new Cons(2, new Cons(3,nfew Nil)))
    nth(2,list)
}