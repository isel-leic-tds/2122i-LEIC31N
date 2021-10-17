import immutable.Stack
import kotlin.test.*

class ImmutableStackTest {
    @Test
    fun `Created Stack Is Empty`() {
        val sut = Stack<String>()
        assertTrue(sut.isEmpty())
        assertNull(sut.top())
        assertNull(sut.pop().first)
        assertSame(sut,sut.pop().second)
    }
    @Test
    fun `Stack with one element`() {
        val sut = Stack<Int>().push(10)
        assertFalse(sut.isEmpty())
        assertEquals(10,sut.top())
        val (elem,sut2) = sut.pop()
        assertEquals(10,elem)
        assertFalse(sut.isEmpty())
        assertTrue(sut2.isEmpty())
    }
}