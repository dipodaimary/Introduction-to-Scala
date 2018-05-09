import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import spark.implicits._

case class Person(ID:Int, name:String, age:Int, numFriends:Int)
def mapper(line:String):Person = {
    val fields = line.split(",")
    val person:Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)
    return person
}

val lines = spark.SparkContext.textFile("fakefriends.csv")
val people = lines.map(mapper).toDS().cache()
spark.stop()