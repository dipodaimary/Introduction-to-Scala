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