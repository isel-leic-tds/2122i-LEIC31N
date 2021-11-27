
class Student5(val number: Int=0, val name:String="") {
    init { print("Init -> ") }
    fun println() = println("$number - ($name)")
}

fun main() {
    Student5().println()
    Student5(47123,"João Antunes").println()
    Student5(4700).println()
    Student5(name="João Antunes").println()
}