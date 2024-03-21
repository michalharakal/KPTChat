package jp.co.qoncept.tensorkotlin

import kotlin.math.*

fun Tensor.pow(tensor: Tensor): Tensor {
    assert(
        { shape == tensor.shape },
        { "Incompatible shapes of tensors: this.shape = ${shape}, tensor.shape = ${tensor.shape}" })
    return Tensor(shape, zipMap(elements, tensor.elements) { a, b -> a.pow(b) })
}

fun Tensor.pow(scalar: Float): Tensor {
    return Tensor(shape, elements.map { it.pow(scalar) })
}

val Tensor.sin: Tensor
    get() = Tensor(shape, elements.map { sin(it) })

val Tensor.cos: Tensor
    get() = Tensor(shape, elements.map { cos(it.toDouble()).toFloat() })

val Tensor.tan: Tensor
    get() = Tensor(shape, elements.map { tan(it.toDouble()).toFloat() })

val Tensor.asin: Tensor
    get() = Tensor(shape, elements.map { asin(it.toDouble()).toFloat() })

val Tensor.acos: Tensor
    get() = Tensor(shape, elements.map { acos(it.toDouble()).toFloat() })

val Tensor.atan: Tensor
    get() = Tensor(shape, elements.map { atan(it.toDouble()).toFloat() })

val Tensor.sinh: Tensor
    get() = Tensor(shape, elements.map { sinh(it.toDouble()).toFloat() })

val Tensor.cosh: Tensor
    get() = Tensor(shape, elements.map { cosh(it.toDouble()).toFloat() })

val Tensor.tanh: Tensor
    get() = Tensor(shape, elements.map { tanh(it.toDouble()).toFloat() })

val Tensor.exp: Tensor
    get() = Tensor(shape, elements.map { exp(it.toDouble()).toFloat() })

val Tensor.log: Tensor
    get() = Tensor(shape, elements.map { ln(it.toDouble()).toFloat() })

val Tensor.sqrt: Tensor
    get() = Tensor(shape, elements.map { sqrt(it.toDouble()).toFloat() })

val Tensor.cbrt: Tensor
    get() = Tensor(shape, elements.map { cbrt(it.toDouble()).toFloat() })

val Tensor.sigmoid: Tensor
    get() = Tensor(shape, elements.map { (1.0 / exp(-it.toDouble())).toFloat() })
