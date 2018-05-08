
val lines = sc.textFile("book.txt")
val words = lines.flatMap(x=>x.split(" "))
val wordCount = words.countByValue()

//with regular expression
val words = lines.flatMap(x=>x.split("\\W+"))
val lowerCaseWords = words.map(x=>x.toLowerCase())

val wordCount = lowerCaseWords.countByValue()