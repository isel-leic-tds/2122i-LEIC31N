package isel.leic.tds.billboard

import java.lang.IllegalArgumentException
import kotlin.test.*

internal class MessageKtTest {

    @Test
    fun `invalid author not accepted`() {
        assertFalse(isValidAuthorId(""))
        assertFalse(isValidAuthorId("   \n\t"))
    }

    @Test
    fun `valid author is accepted`() {
        assertTrue(isValidAuthorId("pg"))
        assertTrue(isValidAuthorId("abc xpto"))
    }

    @Test
    fun `test toAuthorOrNull`() {
        assertNull("  ".toAuthorOrNull())
        assertEquals(Author("name"),"name".toAuthorOrNull())
    }

    @Test
    fun `create Author object`() {
        assertEquals(Author("tds"),Author("tds"))
        assertFailsWith<IllegalArgumentException> { Author("") }
    }
}