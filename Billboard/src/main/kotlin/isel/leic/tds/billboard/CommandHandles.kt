package isel.leic.tds.billboard

/**
 * Type of each command handler.
 * Explicitly separates the action of the command and the presentation of the result.
 * It is possible to do tests on the action, without having tests on the presentation.
 * @property action The action function.
 * @property show The show function.
 */
data class Command<T>(
    /**
     * Performs the action of the command returning the information to present or null if it is to terminate.
     * Receive the parameter given on command line.
     * May throws exception in case of failure with appropriate error message.
     */
   val action: (String?) -> T?,

    /**
     * Displays the result returned by the action.
     * Receive the result information to show.
     */
    val show: (Any) -> Unit = { }
)

/**
 * Build the associative map of command handlers.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The handlers map of all commands.
 */
fun buildHandlers(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to Command( { null }  ),
    "POST" to Command(
        action = { /*param: String? ->*/
            postMessageAction(billboard, author, it)
            it
        },
        show = { println("Message \"$it\" posted by ${author.id}")  }
    ),
    "GET" to Command(
        action = { getMessageAction(billboard, it) },
        show = { (it as Iterable<*>).forEach( ::println ) }
    ),
    "USER" to Command(
        { }, { println("Your user id is ${author.id}") }
    )
)