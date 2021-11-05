package isel.leic.tds.billboard

/**
 * Show all messages from billboard posted by the specified author or by all authors.
 * PROBLEM: Cannot be tested.
 * @param billboard to access Billboard operations
 * @param authorId the author of messages or null for all authors
 */
fun getMessage(billboard: Billboard, authorId: String?) {
    // action
    val messages = getMessageAction(billboard, authorId)
    // show
    messages.forEach(::println)
}

/**
 * Post a message by one author to the billboard in MongoDb.
 * Show the result of the operation.
 * PROBLEM: Cannot be tested.
 * @param billboard to access Billboard operations
 * @param author the author of message
 * @param content the content of message, cannot be null.
 */
fun postMessage(billboard: Billboard, author: Author, content: String?) {
    try {
        // action
        postMessageAction(billboard,author,content)
        // show
        println("Message \"$content\" posted by ${author.id}")
    } catch (ex: Exception) {
        // Exceptional situations
        println("Error: ${ex.message}.")
    }
}
