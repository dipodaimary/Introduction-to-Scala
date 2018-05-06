import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

val conf = new SparkConf().setAppName(appName).setMaster(master)
new SparkContext(conf)

//Paralallized collections
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data)

val lines = sc.textFile("data.txt")
val lineLengths = lines.map(s => s.length)
val totalLength = lineLengths.reduce((a, b) => a + b)