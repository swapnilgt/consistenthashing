package com.swapnil.consitenthashing.presentation.usecase

import androidx.compose.ui.graphics.Color
import javax.inject.Inject

internal class HashToColorCodeConverter @Inject constructor(){

    fun convert(hash: Int): Color {
        val colorCode = hash.toString(16).padStart(6, '0')
        return parseHexColor(colorCode)
    }

    private fun parseHexColor(hexColor: String): Color {
        // Remove '#' if it's present
        val hex = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor

        // Convert hexadecimal string to RGB components
        val red = hex.substring(0, 2).toIntOrNull(16) ?: 0
        val green = hex.substring(2, 4).toIntOrNull(16) ?: 0
        val blue = hex.substring(4, 6).toIntOrNull(16) ?: 0

        // Create Color object from RGB components
        return Color(red = red / 255f, green = green / 255f, blue = blue / 255f)
    }

}