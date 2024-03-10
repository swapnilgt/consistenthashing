package com.swapnil.consitenthashing.domain.request

import com.swapnil.consitenthashing.domain.pojo.AbstractHashElement

internal class GetIndexForAddingRequestUseCase {

    fun execute(
        list: List<AbstractHashElement>,
        targetHashPosition: Int
    ): Int {

        // Empty check ...
        if(list.isEmpty()) {
            return 0
        }

        // Conditions for single element....
        if(list.size == 1) {
            return if(list[0].hashPosition == targetHashPosition) {
                0
            } else if(list[0].hashPosition > targetHashPosition) {
                0
            } else {
                1
            }
        }

        // Conditions for edge cases ...
        if(list[list.size - 1].hashPosition < targetHashPosition || list[0].hashPosition >= targetHashPosition) {
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