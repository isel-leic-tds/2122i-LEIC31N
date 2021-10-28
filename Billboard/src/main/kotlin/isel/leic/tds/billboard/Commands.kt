package isel.leic.tds.billboard

fun getMessage(authorId: String?) {
    println("getMessage: authorId = [${authorId}]")
}

fun postMessage(author: Author, content: String?) {
    println("postMessage: author = [${author}], content = [${content}]")
}