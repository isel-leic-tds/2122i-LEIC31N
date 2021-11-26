package isel.leic.tds.billboard

import isel.leic.tds.billboard.model.ModelException
import isel.leic.tds.billboard.storage.MongoBillboard
import isel.leic.tds.billboard.storage.StorageException
import isel.leic.tds.billboard.ui.readAuthorId
import isel.leic.tds.billboard.ui.readCommand
import isel.leic.tds.mongoDb.MongoDriver

// MONGO_CONNECTION=mongodb+srv://user_tds:tds2122@cluster0.74mwi.mongodb.net/billboard?retryWrites=true&w=majority
/**
 * The application entry point.
 */
fun main() {
    try {
        MongoDriver().use { driver ->
            val handlers = buildHandlers(MongoBillboard(driver), readAuthorId())
            while (true) {
                val (name, parameter) = readCommand()
                val cmd = handlers[name]
                if (cmd == null)
                    println("Invalid command")
                else try {
                    val result = cmd.action(parameter) ?: break
                    cmd.show(result)
                } catch (ex: ModelException) {
                    println("Error: ${ex.message}.")
                }
            }
        }
    }catch (ex: StorageException) {
        println("Access DB error\n${ex.message}")
    }
}

