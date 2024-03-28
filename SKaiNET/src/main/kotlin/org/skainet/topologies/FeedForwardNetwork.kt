package org.skainet.topologies

import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.nn.Module

class FeedForwardNetwork(vararg modules: Module) : Module() {
    private val modulesList = modules.toList()
    override fun forward(input: Tensor): Tensor {
        var tmp = input
        modulesList.forEach { module ->
            tmp = module.forward(tmp)
        }
        return tmp
    }
}