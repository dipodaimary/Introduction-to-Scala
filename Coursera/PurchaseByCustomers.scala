def parseLine(line:String):(Int,Float) = {
    val fields = line.split(",")
    val customerId = fields(0)
    val amountSpent = fields(2)
} 