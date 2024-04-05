typealias Matrix = List<List<Double>>
//typealias Vector = List<Double>
typealias IntVector = List<Int>
//typealias FloatVector = List<Float>

@JvmName("toIntMatrix")
fun IntVector.toMatrix(columns: Int): Matrix = (0 until (this.size / columns)).map { row ->
    (0 until columns).map { col ->
        this[row * columns + col].toDouble()
    }
}

fun Matrix.shape(): List<Int> = listOf(this.size, this[0].size)

fun Matrix.multiply(matrix: Matrix): Matrix {
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

