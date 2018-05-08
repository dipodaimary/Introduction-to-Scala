def parseLine(line:String):(Int,Float) = {
    val fields = line.split(",")
    val customerId = fields(0).toInt
    val amountSpent = fields(2).toFloat
    (customerId,amountSpent)
} 

val input = sc.textFile("customer-orders.csv")
val mappedInput = input.map(parseLine)
val totalByCustomer = mappedInput.reduceByKey((x,y)=>x+y)
totalByCustomer.collect()
totalByCustomer.foreach(println)
val totalSpendByCustomer = totalByCustomer.map(x=>(x._2,x._1)).sortByKey()
totalSpendByCustomer.foreach(println)