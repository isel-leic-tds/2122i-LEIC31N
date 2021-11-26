package isel.leic.tds.billboard

import isel.leic.tds.billboard.model.Author
import isel.leic.tds.billboard.model.getMessageAction
import isel.leic.tds.billboard.model.postMessageAction
import isel.leic.tds.billboard.storage.Billboard
import isel.leic.tds.billboard.ui.getShow
import isel.leic.tds.billboard.ui.postShow
import isel.leic.tds.billboard.ui.userShow
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

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
 * Decorator action to print the elapsed time while executing the decorated action.
 * @param action decorated function
 */
@OptIn(ExperimentalTime::class)
fun <T> timeAction(action: (String?) -> T?) =
    { param: String? ->
        val tm = TimeSource.Monotonic.markNow()
        action(param).also {
            println("Elapsed time = ${tm.elapsedNow()}")
        }
    }


/**
 * Build the associative map of command handlers.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The handlers map of all commands.
 */
fun buildHandlers(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to Command( { null }  ),
    "POST" to Command(
        action = timeAction {
            postMessageAction(billboard, author, it)
            Pair(it,author.id)
        },
        show = ::postShow
    ),
    "GET" to Command(
        action = timeAction { getMessageAction(billboard, it) },
        show = ::getShow
    ),
    "USER" to Command(
        { author.id }, ::userShow
    )
)