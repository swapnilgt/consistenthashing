package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoRequestsPresentException
import javax.inject.Inject
import kotlin.jvm.Throws

internal class GetRightMostRequestIndexForNewNode @Inject constructor() {


    @Throws(NoRequestsPresentException::class)
    fun execute(requests: List<Request>, incomingNode: Node): Int {
        if(requests.isEmpty()) {
            throw NoRequestsPresentException()
        }

        // Handling single element case ...
        if(requests.size == 1) {
            return 0
        }

        // Handling edge cases ...
        if(requests[0].hashPosition > incomingNode.hashPosition
            || requests[requests.size - 1].hashPosition <= incomingNode.hashPosition) {
            return requests.size - 1
        }

        return binarySearchForRightMostIndex(requests, incomingNode)
    }

    private fun binarySearchForRightMostIndex(list: List<Request>, newNode: Node): Int {
        var left = 0
        var right = list.size - 1

        while(right > left) {
            val mid = left + (right - left)/2
            // End condition.
            if(right - left == 1) {
                return left
            }

            if(list[mid].hashPosition <= newNode.hashPosition) {
                left = mid
            } else if(list[mid].hashPosition > newNode.hashPosition) {
                right = mid
            }
        }
        throw IllegalStateException("Should not reach here")
    }
}