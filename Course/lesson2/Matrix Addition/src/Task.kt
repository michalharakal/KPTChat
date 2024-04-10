import Matrix

fun main () {
    val A = listOf(1,2,3,4,5,6).toMatrix(2)
    val B = listOf(4,5,6,7,8,9).toMatrix(2)

    println ("Dimension: " + A.shape())

    A.print()
    B.print()
}

fun Matrix.add(matrix: Matrix): Matrix {
    if (this.shape() != matrix.shape()) {
        throw Exception("Matrixes don't match")
    }
    return this.mapIndexed { i, row ->
        row.mapIndexed { j, cell ->
            cell + matrix[i][j]
        }
    }
}