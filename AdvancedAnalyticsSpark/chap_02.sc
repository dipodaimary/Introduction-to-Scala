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
