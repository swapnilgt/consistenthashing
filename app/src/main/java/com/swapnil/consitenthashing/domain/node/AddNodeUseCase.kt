package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoRequestsPresentException
import javax.inject.Inject

internal class AddNodeUseCase @Inject constructor(
    private val getIndexForAddingNodeUseCase: GetIndexForAddingNodeUseCase,
    private val getRightMostRequestIndexForNewNode: GetRightMostRequestIndexForNewNode
) {

    fun addNode(node: Node, nodes: MutableList<Node>, requests: List<Request>) {
        // Find the index for adding node.
        val addNodeIndex = getIndexForAddingNodeUseCase.execute(nodes, node.hashPosition)

        val breakerNode = if(nodes.size == 1) {
            nodes[0]
        } else {
            null
        }
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
            val currNode = requests[it].node
            var _localIndex = it

            // Check for the existing values of the request.
            requests[_localIndex].node?.let {
                if(it.hashPosition >= requests[_localIndex].hashPosition
                    && it.hashPosition < node.hashPosition ) {
                    return
                }
            }

            // Iterating and updating the values.
            while(requests[_localIndex].node == currNode && (breakerNode == null ||
                        (requests[_localIndex].hashPosition > breakerNode.hashPosition))) {

                requests[_localIndex].node = node
                // Going left in the ring.
                _localIndex = ((_localIndex - 1) + requests.size) % requests.size
            }
        }
    }
}