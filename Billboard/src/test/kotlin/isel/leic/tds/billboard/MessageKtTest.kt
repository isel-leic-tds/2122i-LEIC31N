package isel.leic.tds.billboard

import isel.leic.tds.billboard.model.Author
import isel.leic.tds.billboard.model.isValidAuthorId
import isel.leic.tds.billboard.model.toAuthorOrNull
import java.lang.IllegalArgumentException
import kotlin.test.*

internal class MessageKtTest {
    @Test
    fun `invalid author not accepted`() {
        assertFalse(isValidAuthorId(""))
        assertFalse(isValidAuthorId("   \n\t"))
        assertFalse(isValidAuthorId("  user"))
        assertFalse(isValidAuthorId("user   "))
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
        assertEquals(Author("tds"), Author("tds"))
        assertFailsWith<IllegalArgumentException> { Author("") }
    }
}