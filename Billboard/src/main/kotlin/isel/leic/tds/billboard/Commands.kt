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
    messages.forEach( ::println )
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

/**
 * Result type of command.
 * TERMINATE - To end the application loop.
 * CONTINUE - To continue the application loop.
 */
enum class Result{ TERMINATE, CONTINUE }

/**
 * Build the associative map of commands.
 * Commands are a set of functions of the same type defined by lambda expressions.
 * Each function directly uses the parameters of the buildCommands function.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The command map.
 */
fun buildCommands(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to { Result.TERMINATE },
    "POST" to { param: String? ->
        postMessage(billboard, author, param)
        Result.CONTINUE
    },
    "GET" to {
        getMessage(billboard, it)
        Result.CONTINUE
    },
    "USER" to {
        println(author.id)
        Result.CONTINUE
    }
)

/*  --- Solution disabled to enable functional solution deployed above ---

/**
 * Common functional interface for all commands. There's only one abstract method.
 */
fun interface Command {
    fun execute(param: String?): Result
}

/**
 * Operator to execute the command as a function.
 * Makes the polymorphic call to the execute method.
 */
operator fun Command.invoke(param: String?) = execute(param)

/**
 * Build the associative map of commands.
 * Instantiate objects with lambda expressions that implement the functional interface.
 * The code directly uses the parameters of the buildCommands function.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The command map.
 */
fun buildCommands(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to Command { Result.TERMINATE },
    "POST" to Command { param ->
        postMessage(billboard,author,param)
        Result.CONTINUE
    },
    "GET" to Command { param ->
        getMessage(billboard,param)
        Result.CONTINUE
    }
)
 */