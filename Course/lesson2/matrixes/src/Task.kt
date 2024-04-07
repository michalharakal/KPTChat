import de.jugda.knanogpt.core.tensor.Tensor

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