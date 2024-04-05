import org.junit.Assert
import org.junit.Test

class Test {
    @Test fun testSolution() {
        val A = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
        // 3x2
        val B = listOf(1, 2, 3, 4, 5, 6).toMatrix(2)

        val C = A.multiply(B)

        Assert.assertEquals(64.toDouble(), C[1][1])
    }
}