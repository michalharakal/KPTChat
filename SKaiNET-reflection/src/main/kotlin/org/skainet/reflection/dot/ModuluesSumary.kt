package org.skainet.reflection.dot

import io.github.rchowell.dotlin.DotNodeShape
import io.github.rchowell.dotlin.DotRootGraph
import io.github.rchowell.dotlin.digraph
import org.skainet.nn.Module

fun Module.toDot(): DotRootGraph {

    data class Node(val name: String)
    data class Edge(val from: String, val to: String)

    fun getNodesAndEdges(root: Module): Pair<Set<Node>, Set<Edge>> {
        val nodes = mutableSetOf<Node>()
        val edges = mutableSetOf<Edge>()

        fun iterateModules(module: Module) {
            module.modules.forEach { subModule ->
                nodes.add(Node(module::class.simpleName!!))
                nodes.add(Node(subModule::class.simpleName!!))
                edges.add(Edge(module::class.simpleName!!, subModule::class.simpleName!!))
                iterateModules(subModule)
            }
        }

        iterateModules(root)
        return Pair(nodes, edges)
    }


    return digraph {
        val (nodes, edges) = getNodesAndEdges(this@toDot)

        /*
         for n in nodes:
        uid = str(id(n))
        # for any value in the graph, create a rectangular ('record') node for it
        dot.node(name=uid, label="{ %s | data %.4f | grad %.4f }" % (n.label, n.data, n.grad), shape='record')
        if n._op:
            # if this value is a result of some operation, create an op node for it
            dot.node(name=uid + n._op, label=n._op)
            # and connect this node to it
            dot.edge(uid + n._op, uid)

    for n1, n2 in edges:
        # connect n1 to the op node of n2
        dot.edge(str(id(n1)), str(id(n2)) + n2._op)
         */
        nodes.forEach { node ->
            node {
                label = "{ ${node.name} " // | data %.4f | grad %.4f }" % (, node.data, node.grad)
                shape = DotNodeShape.BOX
            }
        }
    }
}