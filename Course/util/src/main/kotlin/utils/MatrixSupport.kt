typealias Matrix = List<List<Double>>
typealias IntVector = List<Int>

@JvmName("toIntMatrix")
fun IntVector.toMatrix(columns: Int): Matrix = (0 until (this.size / columns)).map { row ->
    if (this.count() % columns != 0) {
        throw Exception("Input is malformed")
    }
    (0 until columns).map { col ->
        this[row * columns + col].toDouble()
    }
}

fun Matrix.shape(): List<Int> = listOf(this.size, this[0].size)

fun Matrix.print() {

    val multirow = this.count() > 1
    var parentheisChar: String = ""

    this.forEachIndexed { i, row ->

        row.forEachIndexed { j, col ->
            if (j != 0) {
                parentheisChar = " "
            } else if (multirow) {
                if (i == 0) {
                    parentheisChar = "⎛"
                } else if (i < this.size - 1) {
                    parentheisChar = "⎜"
                } else {
                    parentheisChar = "⎝"
                }
            } else {
                parentheisChar = "("
            }
            print(parentheisChar + " ")
            print(col)
        }
        if (multirow) {
            if (i == 0) {
                parentheisChar = "⎞"
            } else if (i < this.size - 1) {
                parentheisChar = "⎟"
            } else {
                parentheisChar = "⎠"
            }
        } else {
            parentheisChar = ")"
        }
        println(" " + parentheisChar)
    }
}
