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


val df = spark.read.format("json").load("/home/dd/Documents/ScalaGit/data/flight-data/json/2015-summary.json")

//We can create DataFrames on the fly
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructField,StructType,StringType,LongType}

val myManualSchema = new StructType(Array(
    new StructField("some",StringType,true),
    new StructField("col",StringType,true),
    new StructField("names",LongType,false)
))

val myRows = Seq(Row("Hello",null,1L))
val myRDD = spark.sparkContext.parallelize(myRows)
val myDF = spark.createDataFrame(myRDD,myManualSchema)
myDF.show()

val myDF = Seq(("Hello",2,1L)).toDF("col1","col2","col3")

//select
df.select("DEST_COUNTRY_NAME").show(2)
df.select("DEST_COUNTRY_NAME","ORIGIN_COUNTRY_NAME").show(2)

import org.apache.spark.sql.functions.{expr,col,column}
df.select(
    df.col("DEST_COUNTRY_NAME")
).show()

val dfWithLongColumnName = df.withColumn("This Long Column-Name",expr("ORIGIN_COUNTRY_NAME"))

//
dfWithLongColumnName.selectExpr("`This Long Column-Name`", "`This Long Column-Name` as `new col`").show(2)
dfWithLongColumnName.select(col("This Long Column-Name").as("new_new")).show(2)


//Removing Columns
df.drop("ORIGIN_COUNTRY_NAME").show(2)
//Changing column name with cast
df.withColumn("count2",col("count").cast("long")).show(2)

//Filtering Rows: using either filter or where
df.where("count<2").show(2) //we will stick to this notation
df.filter(col("count")<2).show(2)
//multiple filters
df.where(col("count")<2).where(col("ORIGIN_COUNTRY_NAME") =!="Croatia")