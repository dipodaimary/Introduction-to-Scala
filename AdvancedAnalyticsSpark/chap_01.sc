val rdd = sc.parallelize(Array(1,2,3,4),4)
val rawblocks =  sc.textFile("linkage2")
rawblocks.count
rawblocks.take(10)