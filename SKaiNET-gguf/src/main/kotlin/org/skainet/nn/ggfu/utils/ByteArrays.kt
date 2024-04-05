package org.skainet.nn.ggfu.utils


fun ByteArray.toUByte(): UByte {
    return 0u
}


fun ByteArray.toULong(): ULong {
    if (this.size != 8) throw IllegalArgumentException("ByteArray must be exactly 8 bytes")

    return (this[0].toULong() and 0xFFu) shl 56 or
            (this[1].toULong() and 0xFFu) shl 48 or
            (this[2].toULong() and 0xFFu) shl 40 or
            (this[3].toULong() and 0xFFu) shl 32 or
            (this[4].toULong() and 0xFFu) shl 24 or
            (this[5].toULong() and 0xFFu) shl 16 or
            (this[6].toULong() and 0xFFu) shl 8 or
            (this[7].toULong() and 0xFFu)
}