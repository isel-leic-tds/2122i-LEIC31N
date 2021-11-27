import kotlin.reflect.KProperty

/**
 * Delegated properties:
 * val/var <property name>: <Type> by <expression>
 */

var global = 0 // using for delegate

class D1 {
    var prop: String by MyDelegate()

    val number: Int by lazy {  // Standard delegate
        println("First read...")
        (0..99).random()
    }

    val sameNumber: Int by this::number
    var member: Int by ::global

    // Other standard delegates:
    // - Delegates.observable
    // - in map
}

class MyDelegate {
    operator fun getValue(thisRef: D1, property: KProperty<*>): String {
        println("Reading of '${property.name}' in $thisRef")
        return "xpto"
    }
    operator fun setValue(thisRef: D1, property: KProperty<*>, value: String) {
        println("Writing $value to '${property.name}' in $thisRef.")
    }
}

fun main() {
    val d1 = D1()
    d1.prop = "test"
    println(d1.prop)

    println(d1.number)
    println(d1.number)
    global = 10
    println(d1.member)
    println(d1.sameNumber)

    val x by lazy{ (1..10).random() }
    println( x )
    println( x )
}