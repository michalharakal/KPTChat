package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Tensor

abstract class Module {

    abstract fun forward(input: Tensor): Tensor
}