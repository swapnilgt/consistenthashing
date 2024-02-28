package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.AbstractHashElement
import com.swapnil.consitenthashing.exception.HashLocationOccupied

internal class GetNodeLeftIndexUseCase {

    fun execute(
        list: List<AbstractHashElement>,
        targetHashPosition: Int
    ): Int {
        if(list.isEmpty()) {
            return 0
        }

        if(list.size == 1 && list[0].hashPosition == targetHashPosition) {
            throw HashLocationOccupied()
        } else if(list[0].hashPosition > targetHashPosition) {
            return 0
        } else if(list[list.size - 1].hashPosition < targetHashPosition) {
            return list.size
        }

        return binarySearchLeft(list, targetHashPosition)
    }

    private fun binarySearchLeft(list: List<AbstractHashElement>, targetHashPosition: Int): Int {
        var left = 0
        var right = list.size - 1

        while(left < right) {
            // Check if we have a collision.
            if(list[left].hashPosition == targetHashPosition || list[right].hashPosition == targetHashPosition) {
                throw HashLocationOccupied()
            }
            // Closure .. we have found the point where the new item needs to be inserted.
            if(right - left == 1) {
                return right
            }
            val mid = left + (right - left) / 2
            if(list[mid].hashPosition > targetHashPosition) {
                right = mid
            } else {
                left = mid
            }
        }
        throw IllegalStateException("Should not reach here")
    }
}