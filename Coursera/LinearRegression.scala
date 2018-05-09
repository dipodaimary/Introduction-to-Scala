import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.mllib.optimization.SquaredL2Updater

Logger.getLogger("org").setLevel(Level.ERROR)
    
 // Create a SparkContext using every core of the local machine
val sc = new SparkContext("local[*]", "LinearRegression")

// This reads in x,y number pairs where x is the "label" we want to predict
// and y is the "feature" which is some associated value we can use to predict the label by
// Note that currently MLLib only works properly if this input data is first scaled such that
// it ranges from -1 to 1 and has a mean of 0, more or less! You need to scale it down, then
// remember to scale it back up later.
val trainingLines = sc.textFile("regression.txt")

// And another RDD containing our "test" data that we want to predict values for using our linear model.
// This will expect both the known label and the feature data. In the real world you won't know
// the "correct" value and would just input feature data.
val testingLines = sc.textFile("regression.txt")

// Convert input data to LabeledPoints for MLLib
val trainingData = trainingLines.map(LabeledPoint.parse).cache()
val testData = testingLines.map(LabeledPoint.parse)

val algorithm = new LinearRegressionWithSGD()
algorithm.optimizer.setNumIterations(100).setStepSize(1.0).setUpdater(new SquaredL2Updater()).setRegParam(0.01)
val model = algorithm.run(trainingData)
val predictions = model.predict(testData.map(_.features))
val predictionAndLabel = predictions.zip(testData.map(_.label))
for (prediction <- predictionAndLabel) {
    println(prediction)
}