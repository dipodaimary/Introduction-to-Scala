import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

val conf = new SparkConf().setAppName("keyValueTest")
val sc = new SparkContext(conf)

val lines = sc.textFile("data/kjvdat.txt").map(line => line.toLowerCase)
val pairs = lines.map(x => (x.split(" ")(0), x))