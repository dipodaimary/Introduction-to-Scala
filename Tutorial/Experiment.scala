val flightData = spark.read.option("inferSchema","true").option("header","true").csv("data/flight-data/csv/2015-summary.csv")
flightData.createOrReplaceTempView("flight_data_2015")
val sqlWay = spark.sql(""" 
SELECT DEST_COUNTRY_NAME, COUNT(1)
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
""")

val dataFrameWay = flightData.groupBy("DEST_COUNTRY_NAME").count()
flightData.select(max("count")).take(1).foreach(println)

/*
Structured API Overview
*/
