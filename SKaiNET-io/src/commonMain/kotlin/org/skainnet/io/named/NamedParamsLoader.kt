package org.skainnet.io.named

import jp.co.qoncept.tensorkotlin.Shape
import kotlinx.io.Source
import org.skainet.nn.NamedParameter


interface NamedParamsLoader {
    fun load(
        source: Source,
        propertyName: String,
        shape: Shape,
        namedParameterEvent: (NamedParameter) -> Unit
    )
}
