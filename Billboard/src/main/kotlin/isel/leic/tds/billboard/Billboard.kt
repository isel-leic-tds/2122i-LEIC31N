package isel.leic.tds.billboard

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
 * Implements the billboard operations using a MongoDB instance.
 * @property driver to access MongoDb
 */
class MongoBillboard(val driver: MongoDriver): Billboard {
    override fun postMessage(msg: Message) =
        driver.getCollection<Message>(msg.author.id).insertDocument(msg)

    override fun getAllMessages() =
        driver.getAllCollections<Message>().flatMap{ it.getAllDocuments() }

    override fun getMessagesByAuthor(author: Author) =
        driver.getCollection<Message>(author.id).getAllDocuments()
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