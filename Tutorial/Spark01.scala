val textFile = spark.read.textFile("README.md")
val linesWithSpark = textFile.filter(line => line.contains("Spark"))
textFile.map(line => line.split(" ").size).reduce((a, b) => if (a > b) a else b)
textFile.map(line => line.split(" ").size).reduce((a, b) => Math.max(a, b))
val wordCounts = textFile.flatMap(line => line.split(" ")).groupByKey(identity).count()
linesWithSpark.cache()
linesWithSpark.count()

//Simple scala application
/* SimpleApp.scala */
import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "README.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }
}