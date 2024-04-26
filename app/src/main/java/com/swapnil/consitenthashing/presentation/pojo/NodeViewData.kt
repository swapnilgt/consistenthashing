package com.swapnil.consitenthashing.presentation.pojo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class NodeViewData(
    val bkgColor: Color,
    val borderColor: Color,
    val radius: Dp,
    val radianVal: Float,
    val text: String = "Node"
)