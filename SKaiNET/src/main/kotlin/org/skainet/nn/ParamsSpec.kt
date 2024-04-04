package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Tensor

data class NamedParameter(val name: String, var value: Tensor)

public fun List<NamedParameter>.by(name: String): NamedParameter =
    first { namedParameter -> namedParameter.name.uppercase().startsWith(name.uppercase()) }
