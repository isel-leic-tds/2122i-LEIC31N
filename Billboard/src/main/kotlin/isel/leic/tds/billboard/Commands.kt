package isel.leic.tds.billboard

import isel.leic.tds.mongoDb.*

fun getMessage(driver: MongoDriver, authorId: String?) {
    if (authorId!=null) {
        val messages = driver
            .getCollection<Message>(authorId)
            .getAllDocuments()
        messages.forEach { message ->
            println(message)
        }
    } else TODO()
}

fun postMessage(driver: MongoDriver, author: Author, content: String?) {
    if (content==null)
        println("Error: Missing content.")
    else {
        val col = driver.getCollection<Message>(author.id)
        val res = col.insertDocument(Message(author, content))
        println(
            if (res) "Message \"$content\" posted by ${author.id}"
            else "Error: Post failed."
        )
    }
}