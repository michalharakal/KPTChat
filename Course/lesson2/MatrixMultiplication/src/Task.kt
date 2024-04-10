fun main () {

}

fun Matrix.multiply(matrix: Matrix): Matrix {
    val dimA = this.shape()
    val dimB = matrix.shape()
    if (dimA[0] != dimB[1] || dimA[1] != dimB[0]) {
        throw Exception ("Dimensions of matrixes do not match")
    }
    return (this.indices).map { i ->
        (0 until matrix.shape()[1]).map { j ->
            var sum = 0.toDouble()
            (0 until this.shape()[1]).forEach { k ->
                sum += (this[i][k] * matrix[k][j])
            }
            sum
        }
    }
}