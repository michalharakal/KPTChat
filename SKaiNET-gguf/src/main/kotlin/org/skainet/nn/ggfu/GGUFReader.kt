package org.skainet.nn.ggfu

import kotlinx.io.Source
import kotlinx.io.readByteString
import kotlinx.io.readUByte
import kotlinx.io.readULong


class GGUFReader(private val source: Source) {

    private fun readHeader(): GGUFHeader {
        val magicNumber = source.readByteString(4)
        val version = source.readUByte()
        val tensorsCount = source.readULong()
        val metadata_kv_count = source.readULong()

        return GGUFHeader(magicNumber.toString(), version, tensorsCount, metadata_kv_count)
    }

    fun parseGGUFModel(): GGUFModel {
        val header = readHeader()

        return GGUFModel(header)

        /*
        // read meta
        0uL.rangeTo(metadata_kv_count).forEach {
            source
        }


        val sections = mutableListOf<Section>()
        while (!source.exhausted()) {
            val type = source.readByte()
            val length = source.readIntLe()
            val data: ByteArray = source.readByteArray(length)
            sections.add(Section(type, data))
        }

        return GGUFModel(magicNumber.toString(), sections)

         */
    }
}