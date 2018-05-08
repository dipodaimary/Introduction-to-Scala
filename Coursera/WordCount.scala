val lines = sc.textFile("book.txt")
val words = lines.flatMap(x=>x.split(" "))
val wordCount = words.countByValue()