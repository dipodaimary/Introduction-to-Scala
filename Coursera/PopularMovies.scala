val lines = sc.textFile("u.data")
val movies = lines.map(x=>(x.split("\t")(1).toInt,1))
val movieCount = movies.reduceByKey((x,y)=>x+y)
val movieCountSorted = movieCount.map(x=>(x._2,x._1)).sortByKey()
movieCountSorted.foreach(println)

//Broadcast variable

import org.apache.spark._
def loadMovieName():Map[Int,String] = {
    //Handle character encoding issues
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
    //Create a map of Ints to Strings, and populate it from u.item
    var movieNames:Map[Int,String] = Map()
    val lines = Source.fromFile("u.item").getLines()
    for(line<-lines){
        var fields = line.split("|")
        if(fields.length>1){
            movieNames += (fields(0).toInt -> fields(1))
        }
    }
    return movieNames
}

var nameDict = sc.broadcast(loadMovieName)
//read in each rating line
var lines = sc.textFile("u.data")