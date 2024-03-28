package jp.co.qoncept.tensorkotlin

data class Tensor(val shape: Shape, val elements: FloatArray) {
    constructor(shape: Shape, element: Float = 0.0f) : this(shape, floatArrayOf(shape.volume, element)) {
    }

    internal fun index(indices: IntArray): Int {
        assert(
            { indices.size == shape.dimensions.size },
            { "`indices.size` must be ${shape.dimensions.size}: ${indices.size}" })
        return shape.dimensions.zip(indices).fold(0) { a, x ->
            assert({ 0 <= x.second && x.second < x.first }, { "Illegal index: indices = ${indices}, shape = $shape" })
            a * x.first + x.second
        }
    }

    operator fun get(vararg indices: Int): Float {
        return elements[index(indices)]
    }

    operator fun get(vararg ranges: IntRange): Tensor {
        val size = ranges.size
        val shape = ranges.map { x -> x.endInclusive - x.start + 1 }
        val reversedShape = shape.reversed()
        val indices = IntArray(size)
        val elements = FloatArray(shape.fold(1, Int::times)) {
            var i = it
            var dimensionIndex = size - 1
            for (dimension in reversedShape) {
                indices[dimensionIndex] = i % dimension + ranges[dimensionIndex].start
                i /= dimension
                dimensionIndex--
            }
            get(*indices)
        }
        return Tensor(Shape(*shape), elements)
    }


    private inline fun commutativeBinaryOperation(tensor: Tensor, operation: (Float, Float) -> Float): Tensor {
        val lSize = shape.dimensions.size
        val rSize = tensor.shape.dimensions.size

        if (lSize == rSize) {
            assert(
                { shape == tensor.shape },
                { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })
            return Tensor(shape, zipMap(elements, tensor.elements, operation))
        }

        val a: Tensor
        val b: Tensor
        if (lSize < rSize) {
            a = tensor
            b = this
        } else {
            a = this
            b = tensor
        }
        assert(
            { a.shape.dimensions.endsWith(b.shape.dimensions) },
            { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })

        return Tensor(a.shape, zipMapRepeat(a.elements, b.elements, operation))
    }

    private inline fun noncommutativeBinaryOperation(
        tensor: Tensor,
        operation: (Float, Float) -> Float,
        reverseOperation: (Float, Float) -> Float
    ): Tensor {
        val lSize = shape.dimensions.size
        val rSize = tensor.shape.dimensions.size

        if (lSize == rSize) {
            assert(
                { shape == tensor.shape },
                { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })
            return Tensor(shape, zipMap(elements, tensor.elements, operation))
        } else if (lSize < rSize) {
            assert(
                { tensor.shape.dimensions.endsWith(shape.dimensions) },
                { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })
            return Tensor(tensor.shape, zipMapRepeat(tensor.elements, elements, reverseOperation))
        } else {
            assert(
                { shape.dimensions.endsWith(tensor.shape.dimensions) },
                { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })
            return Tensor(shape, zipMapRepeat(elements, tensor.elements, operation))
        }
    }

    operator fun plus(tensor: Tensor): Tensor {
        return commutativeBinaryOperation(tensor) { lhs, rhs -> lhs + rhs }
    }

    operator fun minus(tensor: Tensor): Tensor {
        return noncommutativeBinaryOperation(tensor, { lhs, rhs -> lhs - rhs }, { lhs, rhs -> rhs - lhs })
    }

    operator fun times(tensor: Tensor): Tensor {
        return commutativeBinaryOperation(tensor) { lhs, rhs -> lhs * rhs }
    }

    operator fun div(tensor: Tensor): Tensor {
        return noncommutativeBinaryOperation(tensor, { lhs, rhs -> lhs / rhs }, { lhs, rhs -> rhs / lhs })
    }

    operator fun times(scalar: Float): Tensor {
        return Tensor(shape, elements.map { it * scalar })
    }

    operator fun div(scalar: Float): Tensor {
        return Tensor(shape, elements.map { it / scalar })
    }

    fun matmul(other: Tensor): Tensor {
        // Scalar multiplication
        if (shape.dimensions.isEmpty() && other.shape.dimensions.isEmpty()) {
            return Tensor(Shape(), floatArrayOf(elements[0] * other.elements[0]))
        }

        // Scalar and Vector multiplication (scalar is `this`)
        if (shape.dimensions.isEmpty()) {
            return Tensor(other.shape, other.elements.map { (it * elements[0]) }.toList().toFloatArray())
        }

        // Scalar and Vector multiplication (scalar is `other`)
        if (other.shape.dimensions.isEmpty()) {
            return Tensor(shape, elements.map { it * other.elements[0] }.toList().toFloatArray())
        }

        // Vector and Matrix multiplication
        if (shape.dimensions.size == 1 && other.shape.dimensions.size == 2) {
            if (shape.dimensions[0] != other.shape.dimensions[0]) throw IllegalArgumentException("Shapes do not align.")
            val result = FloatArray(other.shape.dimensions[1]) { 0f }
            for (i in elements.indices) {
                for (j in 0 until other.shape.dimensions[1]) {
                    result[j] += elements[i] * other.elements[i * other.shape.dimensions[1] + j]
                }
            }
            return Tensor(Shape(other.shape.dimensions[1]), result)
        }

        // Matrix and Matrix multiplication
        if (shape.dimensions.size == 2 && other.shape.dimensions.size == 2) {
            if (shape.dimensions[1] != other.shape.dimensions[0]) throw IllegalArgumentException("Shapes do not align.")
            val newShape = Shape(shape.dimensions[0], other.shape.dimensions[1])
            val result = FloatArray(newShape.volume) { 0f }
            for (i in 0 until shape.dimensions[0]) {
                for (j in 0 until other.shape.dimensions[1]) {
                    for (k in 0 until shape.dimensions[1]) {
                        result[i * newShape.dimensions[1] + j] += elements[i * shape.dimensions[1] + k] * other.elements[k * other.shape.dimensions[1] + j]
                    }
                }
            }
            return Tensor(newShape, result)
        }

        throw IllegalArgumentException("Unsupported tensor shapes for multiplication.")
    }

    override fun toString(): String {
        return when (shape.dimensions.size) {
            1 -> { // 1D tensor
                //println(shape)
                vectorToString()
            }

            2 -> { // 2D tensor
                //println(shape)
                matrixToString()
            }

            else -> "Tensor(${shape}, ${elements.contentToString()})" // higher dimensions
        }
    }

    private fun vectorToString(): String {
        return elements.joinToString(prefix = "[", postfix = "]")
    }

    private fun matrixToString(): String {
        val (rows, cols) = shape.dimensions
        return (0 until rows).joinToString(separator = "\n", prefix = "[\n", postfix = "\n]") { r ->
            (0 until cols).joinToString(prefix = " [", postfix = "]") { c ->
                elements[r * cols + c].toString()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Tensor

        if (shape != other.shape) return false
        if (!elements.contentEquals(other.elements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shape.hashCode()
        result = 31 * result + elements.contentHashCode()
        return result
    }

    companion object {
        fun random(shapeDimension: Int, elements: Int): Tensor =
            Tensor(Shape(shapeDimension), floatArrayOfRandom(elements))
    }
}

operator fun Float.times(tensor: Tensor): Tensor {
    return tensor.times(this)
}

