package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.AbstractHashElement
import com.swapnil.consitenthashing.exception.NoNodePresentException

internal class GetIndexForAddingRequestUseCase {

    fun execute(
        list: List<AbstractHashElement>,
        targetHashPosition: Int
    ): Int {
        // Empty check..
        if(list.isEmpty()) {
            throw NoNodePresentException()
        }

        // Single element check..
        if(list.size == 1) {
            return 0
        }

        // Element on edge check ...
        if(list[list.size - 1].hashPosition < targetHashPosition
            || list[0].hashPosition >= targetHashPosition) {
            return 0
        }

        return binarySearchRight(list, targetHashPosition)
    }

    private fun binarySearchRight(list: List<AbstractHashElement>, targetHashPosition: Int): Int {
        var left = 0
        var right = list.size - 1

        while(left < right) {
            // Closure .. we have found the point where the new item needs to be inserted.
            if(right - left == 1) {
                return right
            }
            val mid = left + (right - left) / 2
            if(list[mid].hashPosition >= targetHashPosition) {
                right = mid
            } else {
                left = mid
            }
        }
        throw IllegalStateException("Should not reach here")
    }
}