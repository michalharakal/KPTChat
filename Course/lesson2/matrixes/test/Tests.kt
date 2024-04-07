import org.junit.Assert
import org.junit.Test
import kotlin.jvm.internal.Intrinsics.Kotlin

class Test {

    @Test fun testMatrixCreation() {
        val A = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)

        Assert.assertEquals(2, A.shape()[0])
        Assert.assertEquals(3, A.shape()[1])
    }

    @Test(expected = Exception::class)
    fun testInvalidArguments() {
        val A = listOf(1, 2, 3, 4, 5).toMatrix(3)
    }

    @Test(expected = Exception::class)
    fun testInvalidArgumentsAdd() {
        val A = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
        val B = listOf(1, 2, 3, 4).toMatrix(2)

        A.add(B)
    }

    @Test fun testSolution() {
        val A = listOf(1, 2, 3, 4, 5, 6).toMatrix(3)
        val B = listOf(7, 8, 9, 10, 11, 12).toMatrix(3)

       val C = A.add(B)
        val D = listOf(8, 10, 12, 14, 16, 18).toMatrix(3)

        Assert.assertEquals(D, C)
    }
}