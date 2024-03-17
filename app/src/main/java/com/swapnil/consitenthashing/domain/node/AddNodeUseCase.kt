package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoRequestsPresentException

internal class AddNodeUseCase(
    private val getIndexForAddingNodeUseCase: GetIndexForAddingNodeUseCase,
    private val getRightMostRequestIndexForNewNode: GetRightMostRequestIndexForNewNode
) {

    fun addNode(node: Node, nodes: MutableList<Node>, requests: List<Request>) {
        // Find the index for adding node.
        val addNodeIndex = getIndexForAddingNodeUseCase.execute(nodes, node.hashPosition)
        // Adding the node.
        nodes.add(addNodeIndex, node)

        // Finding the start index from where we have
        // to start updating the node position of the requests.
        val rightMostRequestIndex: Int? = try {
            getRightMostRequestIndexForNewNode.execute(requests, node)
        } catch (e: NoRequestsPresentException) {
            System.out.println("No requests present")
            null
        }

        // Updating the node reference for the updated requests.
        rightMostRequestIndex?.let {
            var _localIndex = it
            while(
                // Handling the condition when we have completed the full circle and we have reached
                // the index where the node was already set earlier.
                requests[_localIndex].node != node &&
                // Checking if the node is null or not.
                (requests[_localIndex].node == null ||
                // Check if the request node hash position is less than the current node hash position.
                (requests[_localIndex].node != null &&
                requests[_localIndex].node!!.hashPosition <= node.hashPosition))) {

                requests[_localIndex].node = node
                // Going left in the ring.
                _localIndex = ((_localIndex - 1) + requests.size) % requests.size
            }
        }
    }
}