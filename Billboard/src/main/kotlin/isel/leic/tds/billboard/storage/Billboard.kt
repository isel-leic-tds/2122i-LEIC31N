package isel.leic.tds.billboard.storage

import com.mongodb.MongoException
import isel.leic.tds.billboard.model.Author
import isel.leic.tds.billboard.model.Message
import isel.leic.tds.mongoDb.*

/**
 * The billboard basic operations.
 * Contract to be implemented by any concrete database.
 */
interface Billboard {
    /**
     * Posts the [message] to the billboard
     * @param message   the message to be posted
     */
    fun postMessage(msg: Message): Boolean
    /**
     * Gets all messages posted on the billboard, regardless of their author
     * @return  the messages on the billboard
     */
    fun getAllMessages(): Iterable<Message>
    /**
     * Gets all messages posted on the billboard by [author]
     * @param [author] the author
     * @return  the messages from the given author
     */
    fun getMessagesByAuthor(author: Author): Iterable<Message>
}

/**
 * Exception that can be thrown by any Billboard interface operation.
 */
class StorageException(cause: Throwable) : Exception(cause)

/**
 * Executes the function passed as a parameter turning Mongo exceptions into Storage exceptions.
 * @param fx Function to execute that can throw Mongo exceptions.
 * @return The result of executing the function [fx]
 * @throws StorageException if [fx] throws MongoException
 */
fun <T> tryStorage( fx: ()->T ) =
    try { fx() }
    catch(ex: MongoException) { throw StorageException(ex) }

/**
 * Implements the billboard operations using a MongoDB instance.
 * @property driver to access MongoDb
 */
class MongoBillboard(val driver: MongoDriver): Billboard {
    override fun postMessage(msg: Message) = tryStorage {
        driver.getCollection<Message>(msg.author.id).insertDocument(msg)
    }

    override fun getAllMessages() : Iterable<Message> = tryStorage {
        driver.getAllCollections<Message>().flatMap { it.getAllDocuments() }
    }

    override fun getMessagesByAuthor(author: Author) = tryStorage {
        driver.getCollection<Message>(author.id).getAllDocuments()
    }
}

/**
 * Implements the billboard operations using a MutableMap in memory.
 * This implementation must be used in unit tests of commands.
 */
class MemoryBillboard: Billboard {
    // Internal mutable map using author id as the key.
    val data = mutableMapOf<String,List<Message>>()
    // Control the success of postMessage.
    var fail = false

    override fun postMessage(msg: Message): Boolean {
        if (fail) return false
        val id = msg.author.id
        val msgs = data[id]
        data[id] = if (msgs==null) listOf(msg) else msgs + msg
        return true
    }

    override fun getAllMessages() = data.values.flatten()

    override fun getMessagesByAuthor(author: Author) = data[author.id] ?: emptyList()
}