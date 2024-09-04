package org.skainnet.io.named

import jp.co.qoncept.tensorkotlin.Shape
import org.skainet.nn.NamedParameter


interface NamedParamsLoader {
    fun load(
        resourceName: String,
        propertyName: String,
        shape: Shape,
        namedParameterEvent: (NamedParameter) -> Unit
    )
}
