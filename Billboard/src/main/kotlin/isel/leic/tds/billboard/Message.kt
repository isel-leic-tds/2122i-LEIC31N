package isel.leic.tds.billboard

data class Author(val id: String) {
    init {
       require( isValidAuthorId(id) )
    }
}

fun isValidAuthorId(id: String) = id.isNotBlank()

fun String.toAuthorOrNull() = if (isValidAuthorId(this)) Author(this) else null

data class Message(val author:Author, val content:String) {
    override fun toString() = "${author.id}: $content"
}
