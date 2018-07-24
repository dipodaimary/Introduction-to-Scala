import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

val columnNames = Seq("x","y") 

val parsedDF = table.select(columnNames.map(c => col(c)): _*)
val parsedData = parsedDF.rdd.map(s => (s.getLong(0), Vectors.dense(s.getDouble(1)))).cache()


// Cluster the data into two classes using KMeans
val numClusters = 10
val numIterations = 20
val clusters = KMeans.train(parsedData.map(_._2), numClusters, numIterations)
val clustersRDD = clusters.predict(parsedData.map(_._2))
val idClusterRDD = parsedData.map(_._1).zip(clustersRDD)
val colNames = Seq("user_id","cluster")
val resultDF = idClusterRDD.toDF(colNames: _*)
resultDF.groupBy("cluster").count().orderBy(asc("cluster")).show()
resultDF.show()

