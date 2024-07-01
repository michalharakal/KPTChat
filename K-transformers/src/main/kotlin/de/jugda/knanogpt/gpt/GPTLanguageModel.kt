package de.jugda.knanogpt.gpt

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.arange
import de.jugda.knanogpt.transformer.TransformerConfig
import de.jugda.knanogpt.transformer.Block
import  de.jugda.knanogpt.transformer.BatchedLinear
import org.skainet.nn.*
import org.skainet.init.normalInit
import de.jugda.knanogpt.core.tensor.zeros
import org.skainet.activations.softmax
import org.skainet.dsl.sequential

class GPTLanguageModel(private val config: TransformerConfig, override val name: String) : Module() {
    private val lm_head: BatchedLinear
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
            lm_head = BatchedLinear(n_embd, vocab_size)
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

        return logits
    }

    fun generate(input: Tensor, max_new_tokens: Int): Tensor {
        // idx is (B, T) array of indices in the current context
        for (i in 1..max_new_tokens) {
            // crop input to the last block_size tokens
            val idx_cond = input[0..-config.block_size]
            // get the predictions
            val logits = forward(idx_cond)
            // focus only on the last time step
            val last_logits = logits[0..config.block_size, 0..config.block_size]  // # becomes (B, C)
            // apply softmax to get probabilities
            val probs = last_logits.softmax()  // (B, C)
            // sample from the distribution
            //idx_next = torch.multinomial(probs, num_samples=1)  # (B, 1)
            // append sampled index to the running sequence
            //idx = torch.cat((idx, idx_next), dim=1)  # (B, T+1)
        }
        return input
    }
}