package org.skainet.reflection.dot

import io.github.rchowell.dotlin.*
import org.skainet.nn.Module


fun Module.toDotGraph() = graph("ModuleGraph") {
    // Recursive function to add nodes and edges for the module and its submodules
    fun addModuleNodesAndEdges(module: Module) {
            val nodeName = module.name
            // Add edges from the current module to its submodules and recursively process submodules
            module.modules.forEach { submodule ->
                // Add an edge from the current module to the submodule
                nodeName - submodule.name

                // Recursively process the submodule
                addModuleNodesAndEdges(submodule)
            }
    }

    // Start the recursive process with the root module
    addModuleNodesAndEdges(this@toDotGraph)
}

