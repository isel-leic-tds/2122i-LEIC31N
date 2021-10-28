package isel.leic.tds.billboard

fun main() {
    val author = readAuthorId()
    while (true) {
        val (cmd,parameter) = readCommand()
        when(cmd) {
            "GET" -> getMessage(parameter)
            "POST" -> postMessage(author,parameter)
            "EXIT" -> break
            else -> println("Invalid command")
        }
    }
}

typealias Command = Pair<String, String?>

fun readCommand(): Command {
    print("> ")
    return readln().parseCommand()
}

fun String.parseCommand(): Command {
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

