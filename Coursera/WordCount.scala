
val lines = sc.textFile("book.txt")
val words = lines.flatMap(x=>x.split(" "))
val wordCount = words.countByValue()

//with regular expression
val words = lines.flatMap(x=>x.split("\\W+"))
val lowerCaseWords = words.map(x=>x.toLowerCase())

val wordCount = lowerCaseWords.countByValue()

//instead of map we will create RDD
val wordCount = lowerCaseWords.map(x=>(x,1)).reduceByValue((x,y)=>x+y)
val wordCountSorted = wordCount.map(x=>(x._2,x._1)).sortByKey()
wordCountSorted.foreach(println)
for(result<-wordCountSorted){
    val count = result._1
    val word = result._2
    println(s"$word: $count")
}