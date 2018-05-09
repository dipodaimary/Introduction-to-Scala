import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import spark.implicits._

object SparkSQL{
    case class Person(ID:Int, name:String, age:Int, numFriends:Int)
    def mapper(line:String):Person = {
        val fields = line.split(",")
        val person:Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)
        return person
    }
    def main(){//args:Array[String]
        //Set the log level to only print errors
        Logger.getLogger("org").setLevel(Level.ERROR)
        //Use new SparkSession interface in Spark 2.0
        val spark = SparkSession.builder.appName("SparkSQL").master("local[*]").getOrCreate()
        val lines =spark.sparkContext.textFile("fakefriends.csv")
        val people = lines.map(mapper)
        //Infer the schema, and register the Dataset as a table
        val schemaPeople = people.toDS
    }
}