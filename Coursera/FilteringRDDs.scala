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