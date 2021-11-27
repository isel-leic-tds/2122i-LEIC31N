
private class Student3(n: Int, id:String) {
    val number: Int = n
    val name: String = id
    init {
        print("Init -> ")
    }
    constructor() : this(0,"") {
        print("C() -> ")
    }
    fun println() = println("$number - ($name)")
}

fun main() {
    Student3().println()
    Student3(47123,"João Antunes").println()
}