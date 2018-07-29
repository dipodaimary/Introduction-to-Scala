val rawUserArtistData = spark.read.textFile("/home/dd/Documents/spark-git/data/audioscrabber/user_artist_data_small.txt")
rawUserArtistData.take(5).foreach(println)
val userArtistDF = rawUserArtistData.map{line=>
    val Array(user,artist,_*)=line.split(' ')
    (user.toInt,artist.toInt)
}.toDF("user","artist")

userArtistDF.agg(min(user),max(user),main(artist),max(artist)).show
userArtistDF.agg(min("user"),max("user"),min("artist"),max("artist")).show()