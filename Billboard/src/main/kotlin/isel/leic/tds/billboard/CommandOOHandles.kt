package isel.leic.tds.billboard

import isel.leic.tds.billboard.model.Author
import isel.leic.tds.billboard.model.Message
import isel.leic.tds.billboard.model.getMessageAction
import isel.leic.tds.billboard.model.postMessageAction
import isel.leic.tds.billboard.storage.Billboard
import isel.leic.tds.billboard.ui.getShow
import isel.leic.tds.billboard.ui.postShow
import isel.leic.tds.billboard.ui.userShow
import java.sql.Time
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * Base type of each command.
 * Explicitly separates the action of the command and the presentation of the result.
 * It is possible to do tests on the action, without having tests on the presentation.
 */
interface CommandOO<T> {
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
 * Decorator command to provide the action execution time.
 * Prints the elapsed time for executing the decorated command action.
 * @property cmd Decorated command.
 */
class TimeCommand<T>(val cmd:CommandOO<T>) : CommandOO<T> {
    @OptIn(ExperimentalTime::class)
    override fun action(param: String?): T? {
        val tm = TimeSource.Monotonic.markNow()
        return cmd.action(param).also {
            println("Elapsed time = ${tm.elapsedNow()}")
        }
    }
    override fun show(result: Any) = cmd.show(result)
}

/**
 * Build the associative map of command handlers.
 * @param billboard for each command access the billboard operations.
 * @param author The author of the messages to be posted.
 * @return The handlers map of all commands.
 */
fun buildOOHandlers(billboard: Billboard, author: Author) = mapOf(
    "EXIT" to object : CommandOO<Nothing> {
        override fun action(param: String?) = null
        override fun show(result: Any) {}
    },
    "POST" to TimeCommand(object : CommandOO<String> {
        override fun action(param: String?): String? {
            postMessageAction(billboard, author, param)
            return param
        }
        override fun show(result: Any) = postShow(Pair(result,author.id))
    }),
    "GET" to TimeCommand(object : CommandOO<Iterable<Message>> {
        override fun action(param: String?) =
            getMessageAction(billboard, param)
        override fun show(result: Any) = getShow(result)
    }),
    "USER" to object : CommandOO<Unit> {
        override fun action(param: String?) { }
        override fun show(result: Any) = userShow(author.id)
    }
)