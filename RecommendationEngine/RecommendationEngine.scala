import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.{SparkContext, SparkConf}

val df = spark.read.format("csv").option("header","true").load("/home/dd/Documents/ScalaGit/data/online_retail.csv")

case class Rating(userId: Option[Int], productId: Option[Int], rating: Option[Float], timestamp: Option[Long])

object Rating {
  def parseRating(str: String): Rating = {
    val fields = str.split(",")
    assert(fields.size == 8)
    try {
        Rating(Some(fields(6).toInt), Some(fields(1).toInt), Some(fields(3).toFloat), Some(fields(4).toLong))
    } catch {
        case e: Exception => Rating(None,None,None,None)
    }
  }
}
val ratings = {sc.textFile("/home/dd/Documents/ScalaGit/data/online_retail.csv")
  .map(parseRating)
  .toDF()
}
val Array(training, test) = ratings.randomSplit(Array(0.8, 0.2))

// Build the recommendation model using ALS on the training data
val als = new ALS()
  .setMaxIter(5)
  .setRegParam(0.01)
  .setUserCol("userId")
  .setItemCol("movieId")
  .setRatingCol("rating")
val model = als.fit(training)

// Evaluate the model by computing the RMSE on the test data
val predictions = model.transform(test)
  .withColumn("rating", col("rating").cast(DoubleType))
  .withColumn("prediction", col("prediction").cast(DoubleType))

val evaluator = new RegressionEvaluator()
  .setMetricName("rmse")
  .setLabelCol("rating")
  .setPredictionCol("prediction")
val rmse = evaluator.evaluate(predictions)
println(s"Root-mean-square error = $rmse")