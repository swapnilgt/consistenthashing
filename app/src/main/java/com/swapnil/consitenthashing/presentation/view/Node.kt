package com.swapnil.consitenthashing.presentation.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swapnil.consitenthashing.presentation.pojo.NodeViewData
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.node(
    circleRadius: Dp = 100.dp,
    nodeViewData: NodeViewData
) {

    drawCircle(
        color = nodeViewData.bkgColor,
        radius = nodeViewData.radius.toPx(),
        center = Offset(
            this.center.x + cos(nodeViewData.radianVal) * circleRadius.toPx(),
            this.center.y - sin(nodeViewData.radianVal) * circleRadius.toPx()
        )
    )
    drawCircle(
        color = nodeViewData.bkgColor,
        radius = nodeViewData.radius.toPx(),
        style = Stroke(width = 5.dp.toPx()),
        center = Offset(
            this.center.x + cos(nodeViewData.radianVal) * circleRadius.toPx(),
            this.center.y - sin(nodeViewData.radianVal) * circleRadius.toPx()
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun NodePreview() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Canvas(modifier = Modifier.size(screenHeight.coerceAtMost(screenWidth)),
        onDraw = {
            node(
                circleRadius = screenHeight.coerceAtMost(screenWidth)/2,
                nodeViewData = NodeViewData(
                    bkgColor = Color.Red,
                    borderColor = Color.Black,
                    radius = 20.dp,
                    radianVal = Math.PI.toFloat()))
        })
}