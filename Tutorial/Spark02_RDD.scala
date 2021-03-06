import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

valconf = new SparkConf().setAppName(appName).setMaster(master)
new SparkContextconf)

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


//Stand alone application
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

val conf = new SparkConf().setAppName("wordCount")
val sc = new SparkContext(conf)

val input = sc.textFile("data/apodat.txt")
val words = input.flatMap(line=>line.split(","))

//transform into pair and count
val counts = words.map(word=>(word,1)).reduceByKey{case (x,y)=>x+y}
counts.saveAsTextFile("output/wordCounts")

//RDD basics
val conf = new SparkConf().setAppName("squareTest")
val sc = new SparkContext(conf)
val input = sc.parallelize(List(1,2,3,4))
val output = input.map(x=>x*x)
output.saveAsTextFile("output/squareTest")
println(output.collect().mkString(", "))

//Actions
val sum = input.reduce((x,y)=>x+y)
val result = input.aggregate((0, 0))(
(acc, value) => (acc._1 + value, acc._2 + 1),
(acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
val avg = result._1 / result._2.toDouble

//Persist
val result = input.map(x => x * x)
//import something? result.persist(StorageLevel.DISK_ONLY)
println(result.count())
println(result.collect().mkString(","))