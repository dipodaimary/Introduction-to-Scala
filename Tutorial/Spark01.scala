val textFile = spark.read.textFile("README.md")
val linesWithSpark = textFile.filter(line => line.contains("Spark"))
textFile.map(line => line.split(" ").size).reduce((a, b) => if (a > b) a else b)
textFile.map(line => line.split(" ").size).reduce((a, b) => Math.max(a, b))
val wordCounts = textFile.flatMap(line => line.split(" ")).groupByKey(identity).count()
linesWithSpark.cache()