def parseLine(line:String):(Int,Int) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age,numFriends)
}
val lines = sc.textFile("fakefriends.csv")
val rdd = lines.map(parseLine)
val tempDF = rdd.toDF("age","friends")

val totalsByAge = rdd.mapValues(x=>(x,1)).reduceByKey((x,y)=>(x._1+y._1,x._2+y._2))
totalsByAge.mapValues(x=>x._1/x._2).collect().mkString("\n")
val averagesByAge = totalsByAge.mapValues(x=>x._1/x._2)
val result = averagesByAge.collect()
result.sorted.foreach(println)