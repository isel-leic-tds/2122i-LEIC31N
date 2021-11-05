package isel.leic.tds.billboard

/**
 * Functions to read a line from standard input.
 * While we don't have the 1.6 version of the Kotlin library.
 */
data class Author(val id: String) {
    init {
       require( isValidAuthorId(id) )
    }
}

/**
 * Checks whether the given string is a valid author identifier.
 * @param   id  the string to be checked
 * @return  true if [id] can be used as an author identifier, false otherwise
 */
fun isValidAuthorId(id: String) = id.isNotBlank() && id.trim()==id

/**
 * Converts this string to an [Author] instance.
 * @return  the [Author] instance or null if this string is not a valid author identifier.
 */
fun String.toAuthorOrNull() = if (isValidAuthorId(this)) Author(this) else null

/**
 * Represents billboard messages
 * @property author   message author
 * @property content  message content
 */
data class Message(val author:Author, val content:String) {
    init { require(content.isNotBlank()) }
    override fun toString() = "${author.id}: $content"
}