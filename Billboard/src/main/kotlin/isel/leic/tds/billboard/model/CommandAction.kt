package isel.leic.tds.billboard.model

import isel.leic.tds.billboard.storage.Billboard

/**
 * Exception that can be thrown by any action in the model context.
 */
class ModelException(msg: String) : Exception(msg)

/**
 * Returns messages from billboard posted by the specified author or by all authors.
 * QUESTION: Can throw exceptions?
 * @param billboard to access Billboard operations
 * @param authorId the author of messages or null for all authors
 */
fun getMessageAction(billboard: Billboard, authorId: String?) =
    if (authorId != null)
        billboard.getMessagesByAuthor(Author(authorId))
    else
        billboard.getAllMessages()

/**
 * Post a message by one author to the billboard in MongoDb.
 * @param billboard to access Billboard operations
 * @param author the author of message
 * @param content the content of message, cannot be null.
 * @throws ModelException if there is no content.
 * @throws IllegalStateException if post failed
 */
fun postMessageAction(billboard: Billboard, author: Author, content: String?) {
    //require(content!=null) { "Missing content" }
    if (content==null) throw ModelException("Missing content")
    check(billboard.postMessage(Message(author,content))) { "Post failed" }
}