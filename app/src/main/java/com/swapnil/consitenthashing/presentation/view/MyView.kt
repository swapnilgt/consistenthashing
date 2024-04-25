package com.swapnil.consitenthashing.presentation.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swapnil.consitenthashing.presentation.pojo.NodeViewData

@Composable
fun MyView(
    nodeViewData: List<NodeViewData>,
    requestViewData: List<NodeViewData>,
    borderColor: Color = Color.Red,
    width: Dp = 100.dp
) {
    Surface {
        Ring(
            borderColor = borderColor,
            width = width,
            nodeViewData = nodeViewData,
            requestViewData = requestViewData
        )
    }
}