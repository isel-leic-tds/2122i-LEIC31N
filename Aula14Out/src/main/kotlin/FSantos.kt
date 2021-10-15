
data class FSantos(val value: Int, val message: String)

data class Team(val name: String) {
//    override fun equals(other: Any?) = other is Team && other.name == name
//    override fun hashCode() = name.hashCode()
}

fun main() {
    val t1 = Team("Luxemburgo")
    val t2 = Team("Luxemburgo")
    println(t1==t2)
    //println(t1.equals(t2))
    println(t1===t2)

    val m1 = FSantos(3,"abc")
    val m2 = FSantos(3,"isel")
    println(m1.equals(m2))
    println(m1.hashCode())
    println(m2.hashCode())
}