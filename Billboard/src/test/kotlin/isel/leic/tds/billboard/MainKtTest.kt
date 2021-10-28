package isel.leic.tds.billboard

import kotlin.test.*

internal class MainKtTest {

    @Test
    fun `test normal command parseCommand`() {
        val (cmd,param) = " get abc  ".parseCommand()
        assertEquals("GET",cmd)
        assertEquals("abc",param)
    }

    @Test
    fun `test irregular command parseCommand`() {
        val (cmd,param) = "exit".parseCommand()
        assertEquals("EXIT",cmd)
        assertNull(param)
    }

}