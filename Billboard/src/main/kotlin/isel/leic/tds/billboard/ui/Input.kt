package isel.leic.tds.billboard.ui

import isel.leic.tds.billboard.model.Author
import isel.leic.tds.billboard.model.toAuthorOrNull

/**
 * Command line after is parsed.
 * first: name of command in uppercase.
 * second: optional parameter (one or more words)
 */
typealias LineCommand = Pair<String, String?>

/**
 * Reads and parses a command line after write the prompt.
 * @return command parsed
 */
fun readCommand(): LineCommand {
    print("> ")
    return readln().parseCommand()
}

/**
 * Parses a string to extract a command.
 * Pure function to be tested.
 * @return command parsed.
 */
fun String.parseCommand(): LineCommand {
    val line = this.trim()
    val cmd = line.substringBefore(' ').uppercase()
    val param = line.substringAfter(' ',"").ifBlank { null }
    return cmd to param
}

/**
 * Requests the author info to be used when posting billboard messages.
 * @return  the author information
 */
fun readAuthorId(): Author {
    while (true) {
        print("Please provide your user id:")
        readln().toAuthorOrNull()?.apply { return this }
        println("Invalid author.")
    }
}

/**
 * Functions to read a line from standard input.
 * While we don't have the 1.6 version of the Kotlin library.
 */
fun readln() = readLine()!!
fun readlnOrNull() = readLine()