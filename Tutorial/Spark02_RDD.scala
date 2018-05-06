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

//Git tutorial
val input = sc.textFile("data/kjvdat.txt").map(line => line.toLowerCase)
input.cache

val wc = input
  .flatMap(line => line.split("""[^\p{IsAlphabetic}]+"""))  // RDD[String]
  .map(word => (word, 1))                                   // RDD[Tuple2[String,Int]], a.k.a. RDD[(String,Int)]
  .reduceByKey((count1, count2) => count1 + count2)         // RDD[(String,Int)]

val wc = input.flatMap(line=>line.split("""[^\p{IsAlphabetic}]+""")).map(wo
rd=>(word,1)).reduceByKey((count1,count2)=>count1+count2)

println("Writing output to: notebooks/output/kjv-wc2")
wc.saveAsTextFile("output/kjv-wc2")