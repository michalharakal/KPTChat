package jp.co.qoncept.tensorkotlin

import kotlin.math.max
import kotlin.math.min


fun Tensor.maxPool(kernelSize: IntArray, strides: IntArray): Tensor {
    assert({ shape.dimensions.size == 3 }, { "`shape.dimensions.size` must be 3: ${shape.dimensions.size}" })
    assert({ kernelSize.size == 3 }, { "`kernelSize.size` must be 3: ${kernelSize.size}" })
    assert({ kernelSize[2] == 1 }, { "`kernelSize[2]` != 1 is not supported: ${kernelSize[2]}" })
    assert({ strides.size == 3 }, { "`strides.size` must be 3: ${strides.size}" })
    assert({ strides[2] == 1 }, { "`strides[2]` != 1 is not supported: ${strides[2]}" })

    val inRows = shape.dimensions[0]
    val inCols = shape.dimensions[1]
    val numChannels = shape.dimensions[2]

    val filterHeight = kernelSize[0]
    val filterWidth = kernelSize[1]

    val inMinDy = -(filterHeight - 1) / 2
    val inMaxDy = inMinDy + filterHeight - 1
    val inMinDx = -(filterWidth - 1) / 2
    val inMaxDx = inMinDx + filterWidth - 1

    val rowStride = strides[0]
    val colStride = strides[1]

    val outRows = inRows ceilDiv rowStride
    val outCols = inCols ceilDiv colStride

    val elements = DoubleArray(outCols * outRows * numChannels)

    var elementIndex = 0
    for (y in 0 until outRows) {
        val inY0 = y * rowStride
        val inMinY = max(inY0 + inMinDy, 0)
        val inMaxY = min(inY0 + inMaxDy, inRows - 1)

        for (x in 0 until outCols) {
            val inX0 = x * colStride
            val inMinX = max(inX0 + inMinDx, 0)
            val inMaxX = min(inX0 + inMaxDx, inCols - 1)

            for (c in 0 until numChannels) {
                var maxElement = Double.MIN_VALUE
                for (inY in inMinY..inMaxY) {
                    for (inX in inMinX..inMaxX) {
                        maxElement = max(maxElement, this.elements[(inY * inCols + inX) * numChannels + c])
                    }
                }
                elements[elementIndex++] = maxElement
            }
        }
    }

    return Tensor(Shape(outRows, outCols, numChannels), elements)
}

/*
fun Tensor.conv2d(filter: Tensor, strides: IntArray): Tensor {
    val inChannels = filter.shape.dimensions[2]

    assert({ shape.dimensions.size == 3 }, { "`shape.dimensions.size` must be 3: ${shape.dimensions.size}" })
    assert(
        { filter.shape.dimensions.size == 4 },
        { "`filter.shape.dimensions.size` must be 4: ${filter.shape.dimensions.size}" })
    assert({ strides.size == 3 }, { "`strides.size` must be 3: ${strides.size}" })
    assert({ strides[2] == 1 }, { "`strides[2]` != 1 is not supported: ${strides[2]}" })
    assert(
        { shape.dimensions[2] == inChannels },
        { "The number of channels of this tensor and the filter are not compatible: ${shape.dimensions[2]} != ${inChannels}" })

    val inRows = shape.dimensions[0]
    val inCols = shape.dimensions[1]

    val filterHeight = filter.shape.dimensions[0]
    val filterWidth = filter.shape.dimensions[1]

    val inMinDy = -(filterHeight - 1) / 2
    val inMaxDy = inMinDy + filterHeight - 1
    val inMinDx = -(filterWidth - 1) / 2
    val inMaxDx = inMinDx + filterWidth - 1

    val rowStride = strides[0]
    val colStride = strides[1]

    val outRows = shape.dimensions[0] ceilDiv rowStride
    val outCols = shape.dimensions[1] ceilDiv colStride
    val outChannels = filter.shape.dimensions[3]

    val elements = DoubleArray(outCols * outRows * outChannels)

    for (y in 0 until outRows) {
        val inY0 = y * rowStride
        val inMinY = max(inY0 + inMinDy, 0)
        val inMaxY = min(inY0 + inMaxDy, inRows - 1)

        for (x in 0 until outCols) {
            val inX0 = x * colStride
            val inMinX = max(inX0 + inMinDx, 0)
            val inMaxX = min(inX0 + inMaxDx, inCols - 1)

            val inYOffset = inY0 + inMinDy
            val inXOffset = inX0 + inMinDx

            for (inY in inMinY..inMaxY) {
                for (inX in inMinX..inMaxX) {
                    matmuladd(
                        inChannels,
                        outChannels,
                        (inY * inCols + inX) * inChannels,
                        this.elements,
                        ((inY - inYOffset) * filterWidth + (inX - inXOffset)) * inChannels * outChannels,
                        filter.elements,
                        (y * outCols + x) * outChannels,
                        elements
                    )
                }
            }
        }
    }

    return Tensor(Shape(outRows, outCols, outChannels), elements)
}

 */

fun Tensor.conv2d(
    inChannels: Int,
    outChannels: Int,
    kernel: Tensor,
    stride: Int = 1,
    padding: Int = 0,
    dilation: Int = 1, // Note: This implementation does not fully support dilation > 1.
    bias: Boolean = true
): Tensor {
    // Assuming the first dimension of kernel shape is output channels,
    // and the input tensor shape is [channels, height, width].
    val (kernelHeight, kernelWidth) = kernel.shape.dimensions
    val (inputHeight, inputWidth) = this.shape.dimensions

    val outputHeight = ((inputHeight + 2 * padding - dilation * (kernelHeight - 1) - 1) / stride) + 1
    val outputWidth = ((inputWidth + 2 * padding - dilation * (kernelWidth - 1) - 1) / stride) + 1

    val outputElements = DoubleArray(outChannels * outputHeight * outputWidth) { if (bias) 1.0 else 0.0 }
    val outputTensor = Tensor(Shape(outChannels, outputHeight, outputWidth), outputElements)

    for (oc in 0 until outChannels) {
        for (h in 0 until outputHeight) {
            for (w in 0 until outputWidth) {
                var sum = 0.0
                for (ic in 0 until inChannels) {
                    for (kh in 0 until kernelHeight) {
                        for (kw in 0 until kernelWidth) {
                            val ih = h * stride + kh - padding
                            val iw = w * stride + kw - padding
                            if (ih in 0..<inputHeight && iw >= 0 && iw < inputWidth) {
                                sum += this.elements[(ic * inputHeight + ih) * inputWidth + iw] *
                                        kernel.elements[((oc * inChannels + ic) * kernelHeight + kh) * kernelWidth + kw]
                            }
                        }
                    }
                }
                outputTensor.elements[(oc * outputHeight + h) * outputWidth + w] += sum
            }
        }
    }

    return outputTensor
}


internal fun matmuladd(
    inCols1Rows2: Int,
    outCols: Int,
    o1: Int,
    vec: DoubleArray,
    o2: Int,
    mat: DoubleArray,
    oo: Int,
    out: DoubleArray
) {
    for (i in 0 until inCols1Rows2) {
        var elementIndex = oo
        val left = vec[i + o1]
        for (c in 0 until outCols) {
            out[elementIndex] += left * mat[i * outCols + c + o2]
            elementIndex++
        }
    }
}
