package isel.leic.tds.billboard

import isel.leic.tds.mongoDb.MongoDriver

fun main() {
    MongoDriver().use { mongoDriver ->
        val author = readAuthorId()
        while (true) {
            val (cmd, parameter) = readCommand()
            when (cmd) {
                "GET" -> getMessage(mongoDriver,parameter)
                "POST" -> postMessage(mongoDriver,author, parameter)
                "EXIT" -> break
                else -> println("Invalid command")
            }
        }
    }

}

typealias LineCommand = Pair<String, String?>

fun readCommand(): LineCommand {
    print("> ")
    return readln().parseCommand()
}

fun String.parseCommand(): LineCommand {
    val line = this.trim()
    val cmd = line.substringBefore(' ').uppercase()
    val param = line.substringAfter(' ',"").ifBlank { null }
    return cmd to param
}

fun readAuthorId(): Author {
    while (true) {
        print("Please provide your user id:")
        readln().toAuthorOrNull()?.apply { return this }
        println("Invalid author.")
    }
}

fun readln() = readLine()!!
fun readlnOrNull() = readLine()

