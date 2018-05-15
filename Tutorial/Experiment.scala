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

//Selecting distinct rows
df.select("ORIGIN_COUNTRY_NAME","DEST_COUNTRY_NAME").distinct().count()

//Random Samples
val seed = 5
val withReplacement = false
val fraction = 0.5

df.sample(withReplacement,fraction,seed).count()
//Random split
val dataFrames = df.randomSplit(Array(0.25,0.75))
dataFrames(0).count>dataFrames(1).count

//Appending Rows: union
import org.apache.spark.sql.Row
val schema = df.schema
val newRows = Seq(
    Row("New Country","Other Country",5L),
    Row("New Country 2","Other Country 3",1L)
)
val parallelizedRows = spark.sparkContext.parallelize(newRows)
val newDF = spark.createDataFrame(parallelizedRows,schema)

df.union(newDF).where("count=1").where($"ORIGIN_COUNTRY_NAME"=!="United States").show()

//Soting rows
df.sort("count").show(5)
df.orderBy("count","DEST_COUNTRY_NAME").show(5)

df.orderBy(expr("count desc")).show(5)
df.sort(desc("count")).show(5)

//import org.apache.spark.sql.functions.{desc,asc}
df.orderBy(desc("count"),asc("DEST_COUNTRY_NAME")).show(5)
//Tip: df.orderBy(desc_nulls_first("count"),asc_nulls_first("DEST_COUNTRY_NAME")).show(5)

//Sort Within Partitions
spark.read.format("json").load("/home/dd/Documents/ScalaGit/data/flight-data/json/2015-summary.json").sortWithinPartiontions("count")
spark.read.format("json").load("/home/dd/Documents/ScalaGit/data/flight-data/json/2015-summary.json").sortWithinPa
rtitions("count").show()

//Repartition and Coalesce
df.rdd.getNumPartitions
df.repartition(5)
//If filtering through a certain column often it will be wise to partition based on that column
df.repartition(col("DEST_COUNTRY_NAME"))
df.repartition(5,col("DEST_COUNTRY_NAME"))

/*
Coalesce on the other hand, will not incur a full shuffle and will try to combine partitions.
This operation will shuffle your data into five partitions based on the destination country name,
then coalesce them (without a full shuffle)
*/
df.repartition(5,col("DEST_COUNTRY_NAME")).coalesce(2)

//Collecting data
val collectDF = df.limit(10)
collectDF.take(5) //take works with an Integer count
collectDF.show()
collectDF.show(5,false)
collectDF.collect()