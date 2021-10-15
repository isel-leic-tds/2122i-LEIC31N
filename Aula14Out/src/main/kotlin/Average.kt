
//fun String.printQuest() { println("$this?") }

fun readValues(quest: String?=null): List<Int> {
    //quest?.printQuest()
    quest?.apply { println("$this?") }
    val values = mutableListOf<Int>()
    while (true) {
        val v = readln().toIntOrNull() ?: return values
        values.add(v)
    }
}

fun main() {
    val values = readValues("Valores")
    val avg = values.average()
    println("Média = $avg")
    values
        .filter { it > avg }
        .forEach { println(it) }
}