def parseLine(line:String):(String,String,Float) = {
    val fields = line.split(",")
    val stationId = fields(0)
    val entryType = fields(2)
    val temperature = fields(3).toFloat*0.1f*(9.0f/5.0f)+32.0f
    (stationId,entryType,temperature)
}
val lines = sc.textFile("1800.csv")
val parsedLines = lines.map(parseLine)
val minTemps = parsedLines.filter(x=>x._2=="TMIN")
val stationTemps = minTemps.map(x=>(x._1,x._3.toFloat))
val minTempsByStation = stationTemps.reduceByKey((x,y)=>min(x,y))
val minTempsByStation = stationTemps.reduceByKey((x,y)=>if(x>y) y else x)
val results = minTempsByStation
for(result<-results){
    val station = result._1
    val temp = result._2
    val formattedTemp = f"$temp%.2f F"
    println(s"$station minimum temperature: $formattedTemp")
}

//find out the date with most precipitation
def parseLine2(line:String):(String,String,Int) = {
    val fields = line.split(",")
    val date = fields(1)
    val entryType = fields(2)
    val precipitation = fields(3).toInt
    (date,entryType,precipitation)
}
val lines = sc.textFile("1800.csv")
val parsedLines = lines.map(parseLine2)
val percDates = parsedLines.filter(x=>x._2=="PRCP")
val datePrecipitations = percDates.map(x=>(x._1,x._3))
val dateWiseMaxPrecp = datePrecipitations.reduceByKey((x,y)=>if(x>y) x else y)
dateWiseMaxPrecp.toDF("date","percp").sort(desc("percp")).collect().mkString("\n")