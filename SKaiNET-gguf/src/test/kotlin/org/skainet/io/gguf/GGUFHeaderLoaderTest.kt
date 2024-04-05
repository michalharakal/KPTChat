import kotlinx.io.buffered
import kotlinx.io.files.SystemFileSystem
import org.skainet.nn.ggfu.GGUFReader
import kotlin.test.Test
import java.nio.file.Paths
import java.nio.file.Path


class TensorTest {


    fun readResource(): Path? {
        val resourceURL = TensorTest::class.java.classLoader.getResource("sine.gguf")
        if (resourceURL != null) {
            return Paths.get(resourceURL.toURI())
        }
        return null
    }


    @Test
    fun `simple read header gets architecture sine`() {
        val buf =
            SystemFileSystem.source(kotlinx.io.files.Path(readResource()!!.toFile().absolutePath))
        with(GGUFReader(buf.buffered())) {
            val model = parseGGUFModel()
            println(model)

        }
    }
}
