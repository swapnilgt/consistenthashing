package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.domain.pojo.Node

internal class AddNodeUseCase(
    private val getIndexForAddingNodeUseCase: GetIndexForAddingNodeUseCase
) {

    fun addNode(node: Node, nodes: MutableList<Node>) {
        // Find the index for adding node.
        val index = getIndexForAddingNodeUseCase.execute(nodes, node.hashPosition)
        // Adding the node.
        nodes.add(index, node)

        // Finding the start index from where we have
        // to start updating the node position of the requests.

    }
}