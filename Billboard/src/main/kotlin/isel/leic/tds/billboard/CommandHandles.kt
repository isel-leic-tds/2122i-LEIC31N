package isel.leic.tds.billboard

/**
 * Base type of each command.
 * Explicitly separates the action of the command and the presentation of the result.
 * It is possible to do tests on the action, without having tests on the presentation.
 */
interface Command<T> {
    /**
     * Performs the action of the command returning the information to present or null if it is to terminate.
     * @param param Parameter given on command line.
     * @return Information to show as a result or null to terminate.
     * @throws Exception in case of failure with appropriate error message.
     */
    fun action(param: String?): T?

    /**
     * Displays the result returned by the action.
     * @param result Information to show.
     */
    fun show(result: Any): Unit
}

/**
 * Build the associative map of command handlers.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The handlers map of all commands.
 */
fun buildHandlers(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to object : Command<Nothing> {
        override fun action(param: String?) = null
        override fun show(result: Any) {}
    },
    "POST" to object : Command<String> {
        override fun action(param: String?): String? {
            postMessageAction(billboard, author, param)
            return param
        }
        override fun show(result: Any) {
            println("Message \"$result\" posted by ${author.id}")
        }
    },
    "GET" to object : Command<Iterable<Message>> {
        override fun action(param: String?) =
            getMessageAction(billboard, param)
        override fun show(result: Any) {
            (result as Iterable<*>).forEach( ::println )
        }
    },
    "USER" to object : Command<Unit> {
        override fun action(param: String?) { }
        override fun show(result: Any) {
            println("Your user id is ${author.id}")
        }
    }
)