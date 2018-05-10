val rawBlock = sc.textFile("lineage")
val head = rawBlock.take(10)
head.foreach(println)
rawBlock.count()

def isHeader(line:String) : Boolean = {
    line.contains("id_1")
}
head.filter(isHeader).foreach(println)
head.filterNot(isHeader).length
//Alternatively
head.filter(x => !isHeader(x)).length
//Even lesser typing
head.filter(!isHeader(_)).length

val noHeader = rawBlock.filter(x => !isHeader(x))
noHeader.first
/*
spark
spark.sparkSession
*/
val prev = spark.read.csv("lineage")
prev.take(20).foreach(println)
//Better function
prev.show()

val parsed = spark.read.option("header","true").option("nullValues","?").option("inferSchema","true").csv("lineage")
parsed.printSchema()

/*
root |-- id_1: integer (nullable = true)
 |-- id_2: integer (nullable = true)
 |-- cmp_fname_c1: string (nullable = true)
 |-- cmp_fname_c2: string (nullable = true)
 |-- cmp_lname_c1: double (nullable = true)
 |-- cmp_lname_c2: string (nullable = true)
 |-- cmp_sex: integer (nullable = true)
 |-- cmp_bd: string (nullable = true)
 |-- cmp_bm: string (nullable = true)
 |-- cmp_by: string (nullable = true)
 |-- cmp_plz: string (nullable = true)
 |-- is_match: boolean (nullable = true)
*/

/*
Reading other format files
val d1 = spark.read.format("json").load("file.json")
val d2 = spark.read.json("file.json")
*/

/*
write files
d1.write.format("parquet").save("file.parquet")
d1.write.parquet("file.parquet")
//to ignore existing file
d2.write.mode(SaveMode.Ignore).parquet("file.parquet") //override,append,ignore
*/

parsed.rdd.map(_.getAs[Boolean]("is_match")).countByValue()
parsed.groupBy("is_match").count().orderBy($"count".desc).show()
parsed.agg(avg($"cmp_sex").as("avg"),stddev($"cmp_sex").as("stddev")).show()

parsed.createOrReplaceTempView("linkage")

/*spark.sql("""
SELECT is_match, COUNT(*) cnt
FROM linkage
GROUP BY is_match
ORDER BY cnt DESC
""").show()
*/

/*
spark.sql(""" SELECT is_match, COUNT(*) AS count FROM linkage GROUP BY 1""
").show()
*/
//Summary
val summary = parsed.describe()
summary.show()
summary.select("summary","cmp_fname_c1","cmp_fname_c2").show()

val matches = parsed.where("is_match=true")
val matchSummary = matches.describe()

val misses = parsed.where("is_match = false")
val missesSummary = misses.describe()

/*
//Alernative 
val matches = parsed.where("is_match = true")
val matchSummary = matches.describe()
val misses = parsed.filter($"is_match" === false)
val missSummary = misses.describe()
*/

/*
summary statistics
*/

val schema = summary.schema
val longForm = summary.flatMap(row => {
val metric = row.getString(0)
(1 until row.size).map(i => {
(metric, schema(i).name, row.getString(i).toDouble)
})
})
/*
scala> longForm.show()
+-----+------------+-------------------+
|   _1|          _2|                 _3|
+-----+------------+-------------------+
|count|        id_1|          5749132.0|
|count|        id_2|          5749132.0|
|count|cmp_fname_c1|          5749132.0|
|count|cmp_fname_c2|          5749132.0|
|count|cmp_lname_c1|          5749132.0|
|count|cmp_lname_c2|          5749132.0|
|count|     cmp_sex|          5749132.0|
|count|      cmp_bd|          5749132.0|
|count|      cmp_bm|          5749132.0|
|count|      cmp_by|          5749132.0|
|count|     cmp_plz|          5749132.0|
| mean|        id_1|  33324.48559643438|
| mean|        id_2|  66587.43558331935|
| mean|cmp_fname_c1| 0.7129024704436274|
| mean|cmp_fname_c2| 0.9000176718903216|
| mean|cmp_lname_c1| 0.3156278193084133|
| mean|cmp_lname_c2|0.31841283153174377|
| mean|     cmp_sex|  0.955001381078048|
| mean|      cmp_bd|0.22446526708507172|
| mean|      cmp_bm|0.48885529849763504|
+-----+------------+-------------------+
*/
val longDF = longForm.toDF("metric","field", "value")

val wideDF = longDF.groupBy("field").pivot("metric",Seq("count","mean","stddev","min","max")).agg(first("value"))

//function
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.first
def pivotSummary(desc: DataFrame): DataFrame = {
val schema = desc.schema
import desc.sparkSession.implicits._
val lf = desc.flatMap(row => {
val metric = row.getString(0)
(1 until row.size).map(i => {
(metric, schema(i).name, row.getString(i).toDouble)
})
}).toDF("metric", "field", "value")
lf.groupBy("field").
pivot("metric", Seq("count", "mean", "stddev", "min", "max")).
agg(first("value"))
}

val matchSummaryT = pivotSummary(matchSummary)
val missSummaryT = pivotSummary(missesSummary)


//Joining data frames and features extraction
matchSummaryT.createOrReplaceTempView("match_desc")
missSummaryT.createOrReplaceTempView("miss_desc")

spark.sql("""
SELECT a.field, a.count + b.count total, a.mean - b.mean delta
FROM match_desc a INNER JOIN miss_desc b ON a.field = b.field
WHERE a.field NOT IN ("id_1", "id_2")
ORDER BY delta DESC, total DESC
""").show()


/*
Preparing data for production environments
*/

case class MatchData(
    id_1: Int,
	id_2: Int,
	cmp_fname_c1: Option[Double],
	cmp_fname_c2: Option[Double],
	cmp_lname_c1: Option[Double],
	cmp_lname_c2: Option[Double],
	cmp_sex: Option[Int],
	cmp_bd: Option[Int],
	cmp_bm: Option[Int],
	cmp_by: Option[Int],
	cmp_plz: Option[Int],
	is_match: Boolean
)

val matchData = parsed.as[MatchData]
matchData.show()