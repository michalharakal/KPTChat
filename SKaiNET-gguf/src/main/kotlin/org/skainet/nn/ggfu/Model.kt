package org.skainet.nn.ggfu


data class GGUFHeader(
    val magicNumber: String,
    val version: UByte,
    val tensorsCount: ULong,
    val metadata_kv_count: ULong
)

data class GGUFModel(
    val header: GGUFHeader
    //
)

data class Section(
    val type: Byte,
    val data: ByteArray
)