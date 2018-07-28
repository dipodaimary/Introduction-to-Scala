#Transpose DF

val schema = summary.schema
val longForm = summary.flatMap(row => {
    val metric = row.getString(0)
    (1 until row.size).map(i => {
        (metric, schema(i).name, row.getString(i).toDouble)
    })
})

val longDF = longForm.toDF("metric","field","value")

val wideDF = longDF.groupBy("field").
pivot("metric",Seq("count","mean","stddev","min","max")).
agg(first("value"))
wideDF.select("field", "count", "mean").show()

#pivotSummary
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.first

def pivotSummary(desc: DataFrame): DataFrame = {
    val schema = desc.schema
    import desc.sparkSession.implicits._
    val lf = desc.flatMap(row => {
        val metric = row.getString(0)
        (1 until row.size).map(i => {
            (metric,schema(i).name, row.getString(i).toDouble)
        })
    }).toDF("metric","field","value")

    lf.groupBy("field").
    pivot("metric", Seq("count", "mean", "stddev", "min", "max")).
    agg(first("value"))
}

val matchSummaryT = pivotSummary(matchSummary)
val missSummaryT = pivotSummary(missSummary)

# Connecting spark to SQL Hive
val sparkSession = SparkSession.builder.master("local[4]").enableHiveSupport().getOrCreate()

#Preparing Models for production Environments
case class MatchData(
    id_1:Int,
    id_2:Int,
    cmp_fname_c1:Option[Double],
    cmp_fname_c2:Option[Double],
    cmp_lname_c1:Option[Double],
    cmp_lname_c2:Option[Double],
    cmp_sex:Option[Int],
    cmp_bd:Option[Int],
    cmp_bm:Option[Int],
    cmp_by:Option[Int],
    cmp_plz:Option[Int],
    is_match:Boolean
)

val matchData = parsed.as[MatchData]
matchData.show

case class Score(value:Double){
    def +(oi:Option[Int])={
        Score(value+oi.getOrElse(0))
    }
}

def scoreMatchData(md:MatchData):Double = {
    (Score(md.cmp_lname_c1.getOrElse(0.0))+md.cmp_plz+md.cmp_by+md.cmp_bd+md.cmp_bm).value
}

val scored = matchData.map{md=>
    (scoreMatchData(md),md.is_match)
}.toDF("score","is_match")

#Model Evaluation
def crossTabs(scored:DataFrame, t:Double): DataFrame = {
    scored.selectExpr(s"score>= $t as above", "is_match").
    groupBy("above").
    pivot("is_match",Seq("true","false")).
    count()
} 
crossTabs(scored,4.0).show
crossTabs(scored, 2.0).show