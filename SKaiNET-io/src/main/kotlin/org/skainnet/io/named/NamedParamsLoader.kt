package org.skainnet.io.named

import org.skainet.nn.NamedParameter


interface NamedParamsLoader {
    fun emit(event: (NamedParameter) -> Unit)
}
