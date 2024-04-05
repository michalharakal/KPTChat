import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Test {
    lateinit var textContent: String

    @Before
    fun setUp() {
        val bufferedReader: BufferedReader = File("input.txt").bufferedReader()
        textContent = bufferedReader.use { it.readText() }
    }

    @Test
    fun `encode and decode test using resource file`() {
        val tokenizer = CharTokenizer(textContent)
        val encoded = tokenizer.encode(textContent)
        val decoded = tokenizer.decode(encoded)
        assertEquals(textContent, decoded)
    }

    @Test
    fun `encode test`() {
        val tokenizer = CharTokenizer("hello world")
        val encoded = tokenizer.encode("hello")
        assertEquals(listOf(3, 2, 4, 4, 5), encoded)
    }

    @Test
    fun `decode test`() {
        val tokenizer = CharTokenizer("hello world")
        val decoded = tokenizer.decode(listOf(3, 2, 4, 4, 5))
        assertEquals("hello", decoded)
    }

    @Test
    fun `encode and decode test`() {
        val text = "hello world"
        val tokenizer = CharTokenizer(text)
        val encoded = tokenizer.encode(text)
        val decoded = tokenizer.decode(encoded)
        assertEquals(text, decoded)
    }

    @Test
    fun `encode with characters not in tokenizer should fail`() {
        val tokenizer = CharTokenizer("hello")
        try {
            tokenizer.encode("world")
        } catch (e: IllegalStateException) {
            assertEquals("Character not found", e.message)
        }
    }

    @Test
    fun `decode with invalid tokens should fail`() {
        val tokenizer = CharTokenizer("hello")
        try {
            tokenizer.decode(listOf(10, 11))
        } catch (e: IllegalStateException) {
            assertEquals("Token not found", e.message)
        }
    }


}