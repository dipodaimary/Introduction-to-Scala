val rawUserArtistData = spark.read.textFile("/home/dd/Documents/spark-git/data/audioscrabber/user_artist_data_small.txt")
rawUserArtistData.take(5).foreach(println)
val userArtistDF = rawUserArtistData.map{line=>
    val Array(user,artist,_*)=line.split(' ')
    (user.toInt,artist.toInt)
}.toDF("user","artist")

userArtistDF.agg(min(user),max(user),main(artist),max(artist)).show
userArtistDF.agg(min("user"),max("user"),min("artist"),max("artist")).show()

val rawArtistData = spark.read.textFile("/home/dd/Documents/spark-git/data/audioscrabber/artist_data_small.txt")
rawArtistData.map{line=>
    val (id,name) = line.span(_!='\t')
    (id.toInt, name.trim)
}.count

val artistByID = rawArtistData.flatMap{line=>
    val (id,name) = line.span(_!='\t')
    if(name.isEmpty){
        None
    }else{
        try{
            Some((id.toInt,name.trim))
        }catch{
            case _:NumberFormatException => None
        }
    }
}.toDF("id","name")

val rawArtistAlias = spark.read.textFile("/home/dd/Documents/spark-git/data/audioscrabber/artist_alias_small.txt")
val artistAlias = rawArtistAlias.flatMap{line=>
    val Array(artist,alias) = line.split('\t')
    if(artist.isEmpty){
        None
    }else{
        Some((artist.toInt,alias.toInt))
    }
}.collect().toMap
artistByID.filter($"id" isin (1208690,1003926)).show

#Building the first model
import org.apache.spark.sql._
import org.apache.spark.broadcast._

def buildCounts(
    rawUserArtistData: Dataset[String],
    bArtistAlias: Broadcast[Map[Int,Int]]):DataFrame = {
        rawUserArtistData.map{line=>
            val Array(userID,artistID,count) = line.split(' ').map(_.toInt)
            val finalArtistID = bArtistAlias.value.getOrElse(artistID,artistID)
            (userID,finalArtistID,count)
        }.toDF("user","artist","count")
    }

val bArtistAlias = spark.sparkContext.broadcast(artistAlias)
val trainData = buildCounts(rawUserArtistData,bArtistAlias)
trainData.cache()

## broadcast hash join and a little about broadcast variables
import org.apache.spark.ml.recommendation._
import scala.util.Random
val model = new ALS().
setSeed(Random.nextLong()).
setImplicitPrefs(true).
setRank(10).
setRegParam(0.01).
setAlpha(1.0).
setMaxIter(5).
setUserCol("user").
setItemCol("artist").
setRatingCol("count").
setPredictionCol("prediction").
fit(trainData)

val userID = 2093760
val existingArtistIDs = trainData.
filter($"user" === userID).
select("artist").as[Int].collect()
artistByID.filter($"id" isin (existingArtistIDs:_*)).show()

def makeRecommendations(
model: ALSModel,
userID: Int,
howMany: Int): DataFrame = {
val toRecommend = model.itemFactors.
select($"id".as("artist")).
withColumn("user", lit(userID))
model.transform(toRecommend).
select("artist", "prediction").
orderBy($"prediction".desc).
limit(howMany)
}
val topRecommendations = makeRecommendations(model, userID, 5)
topRecommendations.show()

val recommendedArtistIDs =
topRecommendations.select("artist").as[Int].collect()
artistByID.filter($"id" isin (recommendedArtistIDs:_*)).show()