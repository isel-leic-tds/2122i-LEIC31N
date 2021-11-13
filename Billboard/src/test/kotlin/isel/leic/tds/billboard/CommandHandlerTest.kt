package isel.leic.tds.billboard

import org.junit.jupiter.api.Test
import kotlin.test.*

@Suppress("UNCHECKED_CAST")
internal class CommandHandlerTest {
    companion object {
        val billboard = MemoryBillboard()
        init {
            billboard.postMessage(Message(Author("a"),"msg 1"))
            billboard.postMessage(Message(Author("a"),"msg 2"))
            billboard.postMessage(Message(Author("b"),"msg 3"))
        }
        val handlers = buildHandlers(billboard, Author("c"))
    }
    @Test
    fun `invalid command`() {
        assertNull(handlers["UNKNOWN"])
        val (name,param) = "Qualquer coisa".parseCommand()
        assertNull(handlers[name])
    }
    @Test
    fun `exit command`() {
        val cmd = handlers["Exit ...".parseCommand().first]
        assertNotNull(cmd)
        assertNull(cmd.action("kjaskja"))
    }
    @Test
    fun `get all messages`() {
        val cmd = handlers["GET"]
        assertNotNull(cmd)
        val sut = cmd.action(null) as Iterable<Message>
        assertEquals(3, sut.count())
        assertEquals(listOf("msg 1","msg 2","msg 3"), sut.map { it.content }.sorted())
    }
    @Test
    fun `get all messages by author`() {
        val line = "get b".parseCommand()
        val cmd = handlers[line.first]
        assertNotNull(cmd)
        val sut = cmd.action(line.second) as Iterable<Message>
        assertEquals(1, sut.count())
        assertEquals("msg 3", sut.first().content)
    }
    @Test
    fun `normal post`() {
        val (name,param) = "post msg 4".parseCommand()
        val res = assertNotNull(handlers[name]).action(param)
        assertEquals(param,res)
        assertEquals(param, billboard.data["c"]?.first()?.content)
        billboard.data.remove("c")
    }
    @Test
    fun `post without content`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            handlers["POST"]?.action?.invoke(null)
        }
        assertEquals("Missing content",ex.message)
    }
    @Test
    fun `post failed`() {
        billboard.fail = true
        val ex = assertFailsWith<IllegalStateException> {
            handlers["POST"]!!.action("msg 4")
        }
        billboard.fail = false
        assertEquals("Post failed",ex.message)
    }
}