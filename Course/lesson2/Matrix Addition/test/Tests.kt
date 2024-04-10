import org.junit.Assert
import org.junit.Test

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

        A.print()
        B.print()

        val C = A.add(B)
        val D = listOf(8, 10, 12, 14, 16, 18).toMatrix(3)

        val E = listOf(1,2,3,4,5,6).toMatrix(2)
        E.print()
        Assert.assertEquals(D, C)
    }
}