
private class Student4(n: Int=0, id:String="") {
    val number: Int = n
    val name: String = id
    init { print("Init -> ") }
    fun println() = println("$number - ($name)")
}

fun main() {
    Student4().println()
    Student4(47123,"João Antunes").println()
    Student4(4700).println()
    Student4(id="João Antunes").println()
}