import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types._
import org.apache.spark.{SparkContext, SparkConf}

val df = spark.read.format("csv").option("header","true").load("/home/dd/Documents/ScalaGit/data/online_retail.csv")

case class Rating(userId: Int, productId: Int, rating: Float, timestamp:Long)


val df2 = {df.selectExpr(
    "cast(CustomerID as Int) as CustomerID",
    "cast(StockCode as Int) as StockCode",
    "cast(Quantity as Float) as Quantity",
    "InvoiceDate as TimeStamp"
)
}
val df3 = df2.withColumn("TimeStamp",unix_timestamp($"TimeStamp", "yyyy/MM/dd HH:mm:ss").cast(TimestampType))
val df32 = df3.filter("CustomerID is not null").filter("StockCode is not null").filter("Quantity is not null").filter("TimeStamp is not null")
/*
def parseRating(fields: Row): Rating = {
    Rating(fields(6).toInt,fields(1).toInt,fields(3).toFloat, fields(4).toLong)
}
val ratings = {
  df.map(parseRating)
  .toDF()
}
*/
val Array(training, test) = df32.randomSplit(Array(0.8, 0.2))
// Build the recommendation model using ALS on the training data
val als = {new ALS()
  .setMaxIter(5)
  .setRegParam(0.01)
  .setUserCol("CustomerID")
  .setItemCol("StockCode")
  .setImplicitPrefs(true)
  .setRatingCol("Quantity")
}
val model = als.fit(training)

// Evaluate the model by computing the RMSE on the test data
val predictions = {model.transform(test)
  .withColumn("Quantity", col("Quantity").cast(DoubleType))
  .withColumn("prediction", col("prediction").cast(DoubleType))
}
val predictions2 = predictions.filter("CustomerID is not null").filter("StockCode is not null").filter("prediction is not null")
val evaluator = {new RegressionEvaluator()
  .setMetricName("rmse")
  .setLabelCol("Quantity")
  .setPredictionCol("prediction")
}
val rmse = evaluator.evaluate(predictions2)
println(s"Root-mean-square error = $rmse")