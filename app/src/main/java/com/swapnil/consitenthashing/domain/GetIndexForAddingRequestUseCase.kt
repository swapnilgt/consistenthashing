package com.swapnil.consitenthashing.domain

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
        if(list[list.size - 1].hashPosition < targetHashPosition) {
            return list.size
        }

        if(list[0].hashPosition >= targetHashPosition) {
            return 0
        }

        return 0
    }
}