import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.Row

case class Rating(userId: Int, movieId: Int, rating: Float, timestamp: Long)
def parseRating(str: String): Rating = {
  val fields = str.split("::")
  assert(fields.size == 4)
  Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat, fields(3).toLong)
}

val ratings = spark.read.format("csv").option("header","true").load("/tmp/ml-latest-small/ratings.csv")//.map(parseRating).toDF()
case class Rating(userId:Int, movieId:Int, rating:Double)
val df = ratings.map{case Row(userId:String,movieId:String,rating:String,timestamp:String)=>Rating(userId.toInt,movieId.toInt,rating.toDouble)}
val Array(training, test) = df.randomSplit(Array(0.8, 0.2))

// Build the recommendation model using ALS on the training data
val als = new ALS().
  setMaxIter(5).
  setRegParam(0.01).
  setUserCol("userId").
  setItemCol("movieId").
  setRatingCol("rating")

val model = als.fit(training)

// Evaluate the model by computing the RMSE on the test data
// Note we set cold start strategy to 'drop' to ensure we don't get NaN evaluation metrics
model.setColdStartStrategy("drop")
val predictions = model.transform(test)

val evaluator = new RegressionEvaluator().
  setMetricName("rmse").
  setLabelCol("rating").
  setPredictionCol("prediction")
val rmse = evaluator.evaluate(predictions)
println(s"Root-mean-square error = $rmse")

// Generate top 10 movie recommendations for each user
val userRecs = model.recommendForAllUsers(10)
// Generate top 10 user recommendations for each movie
val movieRecs = model.recommendForAllItems(10)
