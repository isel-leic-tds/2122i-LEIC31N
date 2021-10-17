import mutable.Stack
import kotlin.test.*

class MutableStackTest {
    @Test
    fun `Created Stack Is Empty`() {
        val sut = Stack<String>()
        assertTrue(sut.isEmpty())
        assertNull(sut.top())
        assertFailsWith<IllegalStateException> { sut.pop() }
    }
    @Test
    fun `Stack with one element`() {
        val sut = Stack<Int>()
        sut.push(10)
        assertFalse(sut.isEmpty())
        assertEquals(10,sut.top())
        assertEquals(10,sut.pop())
        assertTrue(sut.isEmpty())
    }
}