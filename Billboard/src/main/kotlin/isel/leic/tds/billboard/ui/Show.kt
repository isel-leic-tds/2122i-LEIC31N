package isel.leic.tds.billboard.ui

/**
 * Displays the result of the POST command.
 * @param arg The pair (content, author id) of the posted message.
 */
fun postShow(arg: Any) {
    val (content, authorId) =  arg as Pair<*,*>
    println("Message \"$content\" posted by $authorId")
}

/**
 * Displays the result of the GET command.
 * @param arg The messages to display
 */
fun getShow(arg: Any) {
    (arg as Iterable<*>).forEach( ::println )
}

/**
 * Displays the result of the USER command.
 * @param authorId The user identification
 */
fun userShow(authorId: Any) {
    println("Your user id is $authorId")
}