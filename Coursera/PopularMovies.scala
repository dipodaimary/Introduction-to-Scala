val lines = sc.textFile("u.data")
val movies = lines.map(x=>(x.split("\t")(1).toInt,1))
val movieCount = movies.reduceByKey((x,y)=>x+y)
val movieCountSorted = movieCount.map(x=>(x._2,x._1)).sortByKey()