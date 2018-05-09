import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import spark.implicits._
import scala.io.Source
import java.nio.charset.CodingErrorAction
import scala.io.Codec
import org.apache.spark.sql.functions._
import spark.implicits._

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

final case class Movie(movieId:Int)
val spark = SparkSession.builder.appName("SparkSQL").master("local[*]").getOrCreate()
val lines = spark.sparkContext.textFile("u.data").map(x=>Movie(x.split("\t")(1).toInt))
val moviesDS = lines.toDS()
val topMoviesIds = moviesDS.groupBy("movieId").count().orderBy(desc("count")).cache()
topMoviesIds.show()
spark.stop()