val rdd = sc.parallelize(Array(1,2,3,4),4)
val rawblocks =  sc.textFile("/home/dd/Documents/spark-git/data/linkage2")
rawblocks.count
rawblocks.take(10)
val head = rawblocks.take(40)
def isHeader(line:String):Boolean={
    line.contains("id_1")
}
head.filterNot(isHeader).foreach(println)
head.filter(x => !isHeader(x)).foreach(println)

val parsed = spark.read.
option("header","true").
option("nullValue","?").
option("inferSchema","true").
csv("/home/dd/Documents/spark-git/data/linkage2")

df.take(100).write.format("parquet).save("/tmp/out/")