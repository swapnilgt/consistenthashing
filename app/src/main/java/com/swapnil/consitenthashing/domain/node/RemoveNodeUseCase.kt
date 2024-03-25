package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoNodePresentException
import com.swapnil.consitenthashing.exception.NodeNotPresentException

internal class RemoveNodeUseCase(
    private val getRightMostRequestIndexForNewNode: GetRightMostRequestIndexForNewNode
) {

    fun removeNode(node: Node, nodes: MutableList<Node>, requests: List<Request>) {
        if(nodes.size == 0) {
            throw NoNodePresentException()
        }

        if(!nodes.contains(node)) {
            throw NodeNotPresentException()
        }

        // Getting the node index.
        // Could have written with custom implementation
        // of binary search but for now using the inbuilt one.
        val nodeRemovedIndex = nodes.indexOf(node)

        // Getting the node for next node to which the requests would be transferred.
        val newNode = getNewNode(nodes, nodeRemovedIndex)


        // Removing the node.
        nodes.remove(node)

        // Updating the request list with the new value.
        updateRequestList(requests, node, newNode)
    }

    private fun updateRequestList(
        requests: List<Request>,
        oldNode: Node,
        newNode: Node?
    ) {
        // In case the new node is null.
        // This means there is no node present in the ring.
        if(newNode == null) {
            requests.forEach {
                it.node = null
            }
            return
        }

        // Finding the rightmost index for the older node in the request list.
        val rightMostRequestIndex = getRightMostRequestIndexForNewNode.execute(requests, oldNode)

        // Updating the value with the new node value.
        var _localIndex = rightMostRequestIndex
        while(requests[_localIndex].node == oldNode) {
            requests[_localIndex].node = newNode
            // Going left in the ring.
            _localIndex = ((_localIndex - 1) + requests.size) % requests.size
        }
    }

    private fun getNewNode(nodes: List<Node>, nodeRemovedIndex: Int): Node? {
        return if(nodes.size == 1) {
            null
        } else {
            if(nodeRemovedIndex == nodes.size - 1) {
                nodes[0]
            } else {
                nodes[nodeRemovedIndex + 1]
            }
        }
    }
}