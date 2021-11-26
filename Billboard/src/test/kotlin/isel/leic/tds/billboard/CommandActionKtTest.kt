package isel.leic.tds.billboard

import isel.leic.tds.billboard.model.*
import isel.leic.tds.billboard.storage.MemoryBillboard
import kotlin.test.*

internal class CommandActionKtTest {
    companion object {
        val billboard = MemoryBillboard()
        init {
            billboard.postMessage(Message(Author("a"),"msg 1"))
            billboard.postMessage(Message(Author("a"),"msg 2"))
            billboard.postMessage(Message(Author("b"),"msg 3"))
        }
    }
    @Test
    fun `get all messages`() {
        val sut = getMessageAction(billboard,null)
        assertEquals(3, sut.count())
        assertEquals(listOf("msg 1","msg 2","msg 3"), sut.map { it.content }.sorted())
    }
    @Test
    fun `get all messages by author`() {
        val sut = getMessageAction(billboard,"b")
        assertEquals(1, sut.count())
        assertEquals("msg 3", sut.first().content)
    }
    @Test
    fun `normal post`() {
        postMessageAction(billboard, Author("c"),"msg 4")
        assertEquals("msg 4", billboard.data["c"]?.first()?.content)
        billboard.data.remove("c")
    }
    @Test
    fun `post without content`() {
        val ex = assertFailsWith<ModelException> {
            postMessageAction(billboard, Author("c"),null)
        }
        assertEquals("Missing content",ex.message)
    }
    @Test
    fun `post failed`() {
        billboard.fail = true
        val ex = assertFailsWith<IllegalStateException> {
            postMessageAction(billboard, Author("c"),"msg 4")
        }
        billboard.fail = false
        assertEquals("Post failed",ex.message)
    }

}