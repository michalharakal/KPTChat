package de.jugda.knanogpt.core.data

import de.jugda.knanogpt.core.CharTokenizer
import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.Shape
import java.nio.file.Files
import java.nio.file.Paths

class ResourcesDataProvider(resourceName: String) : DataProvider<Tensor> {

    private val tokenizer: CharTokenizer
    private val textContent: String

    init {
        val url = ResourcesDataProvider::class.java.classLoader.getResource(resourceName)
        val uri = url?.toURI() ?: throw IllegalArgumentException("File not found in resources.")

        val path = Paths.get(uri)
        textContent = String(Files.readAllBytes(path))
        tokenizer = CharTokenizer(textContent)
    }

    override fun load(): Tensor =
        Tensor(Shape(textContent.length), tokenizer.encode(textContent).map {
            it.toDouble()
        }.toDoubleArray())

    override val vocabSize: Long
        get() = tokenizer.vocabularySize()
}