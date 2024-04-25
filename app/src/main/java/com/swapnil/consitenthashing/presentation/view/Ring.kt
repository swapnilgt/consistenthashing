package com.swapnil.consitenthashing.presentation.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swapnil.consitenthashing.presentation.pojo.NodeViewData

@Composable
fun Ring(
    nodeViewData: List<NodeViewData> = emptyList(),
    requestViewData: List<NodeViewData> = emptyList(),
    borderColor: Color = Color.Red,
    width: Dp = 100.dp) {
    Canvas(modifier = Modifier.size(width + 10.dp), onDraw = {
        drawCircle(
            color = borderColor,
            radius = (width/2).toPx(),
            style = Stroke(width = 5.dp.toPx()
            )
        )

        nodeViewData.forEach {
            node(
                circleRadius = width/2,
                nodeViewData = it
            )
        }

        requestViewData.forEach {
            node(
                circleRadius = width/2,
                nodeViewData = it
            )
        }

    })
}

@Composable
@Preview(showSystemUi = true)
fun RingPreview() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Ring(
        width = screenHeight.coerceAtMost(screenWidth),
        nodeViewData = mutableListOf(NodeViewData(
            bkgColor = Color.Red,
            borderColor = Color.Black,
            radius = 20.dp,
            radianVal = 0.0f
        )),
        requestViewData = mutableListOf(NodeViewData(
            bkgColor = Color.Blue,
            borderColor = Color.Red,
            radius = 15.dp,
            radianVal = Math.PI.toFloat())
        )
    )
}