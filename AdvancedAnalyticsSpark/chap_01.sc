val rdd = sc.parallelize(Array(1,2,3,4),4)
val rawblocks =  sc.textFile("/home/dd/Documents/spark-git/data/linkage2")
rawblocks.count
rawblocks.take(10)
