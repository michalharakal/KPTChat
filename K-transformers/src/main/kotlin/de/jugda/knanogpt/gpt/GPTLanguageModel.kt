package de.jugda.de.jugda.knanogpt.gpt

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.arange
import de.jugda.knanogpt.transformer.TransformerConfig
import de.jugda.knanogpt.transformer.Block
import org.skainet.nn.*
import org.skainet.init.normalInit
import de.jugda.knanogpt.core.tensor.zeros
import org.skainet.dsl.sequential

class GPTLanguageModel(config: TransformerConfig, override val name: String) : Module() {
    private val lm_head: Linear
    private val ln_f: LayerNorm
    private val blocks: Module
    private val position_embedding_table: Embedding
    private val token_embedding_table: Embedding

    init {
        // each token directly reads off the logits for the next token from a lookup table
        with(config) {
            token_embedding_table = Embedding(vocab_size, n_embd)
            position_embedding_table = Embedding(block_size, n_embd)
            blocks = sequential {
                List(n_layer) {
                    Block(config)
                }
            }

            ln_f = LayerNorm(n_embd)
            lm_head = Linear(vocab_size, n_embd)
        }
        initWeights()
    }

    private fun initWeights() {
        modules.forEach { module ->
            if (module is Linear) {
                module.params.by("W")?.let { weights ->
                    weights.value = normalInit(weights.value.shape, 0.0, 0.02)
                }
                module.params.by("B")?.let { bias ->
                    bias.value = bias.value.shape.zeros()
                }
            }
            if (module is Embedding) {
                module.params.by("W")?.let { weights ->
                    weights.value = normalInit(weights.value.shape, 0.0, 0.02)
                }
            }
        }
    }


    override val params: List<NamedParameter>
        get() = modules.map { module -> module.params }.flatten()
    override val modules: List<Module>
        get() = listOf(token_embedding_table, position_embedding_table, ln_f, lm_head) + blocks

    override fun forward(input: Tensor): Tensor {
        val (B, T) = input.shape.dimensions
        val tok_emb = token_embedding_table(input)  //# (B,T,C)
        val pos_emb = position_embedding_table(arange(end = T.toDouble())) // # (T,C)
        val emb = tok_emb + pos_emb // # (B,T,C)
        val bl = blocks(emb)
        val ln = ln_f(bl)
        print("ln shape: ${ln.shape}")
        val logits = lm_head(ln) // # (B,T,V)


        return ln

        /*
        val (B, T) = input.shape.dimensions
        return (token_embedding_table(input) + position_embedding_table(arange(end = B.toDouble()))) // # (B,T,C)
            .let { blocks.fold(it) { acc, block -> block(acc) } } // # (B,T,C)
            .let { ln_f(it) } // # (B,T,C)
            .let { lm_head(it) } // # (B,T,V)


         */

    }
}