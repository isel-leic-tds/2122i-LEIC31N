package immutable

private class Node<T>(val value: T, val next: Node<T>?)

class Stack<T> {
    private val head: Node<T>?
    constructor() { head=null }
    private constructor(node: Node<T>?) { head = node }
    fun push(elem: T) = Stack(Node(elem,head))
    fun pop(): Pair<T?,Stack<T>> =
        if (head==null) Pair(null,this)
        else Pair(head.value,Stack(head.next))
    fun top(): T? = head?.value
    fun isEmpty() = head==null
}

fun main() {
    var stk = Stack<Int>()
    stk = stk.push(10)
    println(stk.isEmpty())  // false
    println(stk.top())      // 10
    stk = stk.push(20)
    while (!stk.isEmpty()) {
        val pair = stk.pop()
        stk = pair.second
        println(pair.first)  // 20, 10
    }
}