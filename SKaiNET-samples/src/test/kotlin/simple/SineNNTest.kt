package simple

import de.jugda.simple.SineNN
import de.jugda.simple.of
import kotlin.test.Test
import kotlin.test.assertEquals


class SineNNTest {

    @Test
    fun `SinneNN is dense neural network with 2 hihhen layers`() {
        val sine = SineNN()
        val predicted = sine.forward(sine.of(kotlin.math.PI / 0.5f))
        assertEquals(.3f, predicted[0])
    }
}