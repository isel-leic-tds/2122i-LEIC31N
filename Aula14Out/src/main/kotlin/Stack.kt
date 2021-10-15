
private class Node<T>(val value: T, val next: Node<T>?)

class Stack<T> {
    private var head: Node<T>? = null
    fun push(elem: T) { head = Node(elem,head) }
    fun pop(): T? {
        val h = head ?: return null
        return h.value.also { head=h.next }
    }
    fun top(): T? = head?.value
    fun isEmpty() = head==null
}

fun main() {
    val stk = Stack<Int>()
    stk.push(10)
    println(stk.isEmpty())  // false
    println(stk.top())      // 10
    stk.push(20)
    while (!stk.isEmpty())
        println(stk.pop())  // 20, 10
}