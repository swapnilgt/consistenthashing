package com.swapnil.consitenthashing.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swapnil.consitenthashing.MainViewModel
import com.swapnil.consitenthashing.presentation.pojo.NodeViewData

@Composable
internal fun MyView(
    nodeViewData: List<NodeViewData> = emptyList(),
    requestViewData: List<NodeViewData> = emptyList(),
    borderColor: Color = Color.Red,
    width: Dp = 100.dp,
    mainViewModel: MainViewModel = viewModel()
) {
    Surface {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Ring(
                    borderColor = borderColor,
                    width = width,
                    nodeViewData = nodeViewData,
                    requestViewData = requestViewData
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { mainViewModel.addRequest() },
                        enabled = nodeViewData.isNotEmpty()
                    ) {
                        Text(text = "Add Request")
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { mainViewModel.addNode() }
                    ) {
                        Text(text = "Add Node")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MyViewPreview() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    MyView(
        width = screenWidth.coerceAtMost(screenHeight),
        nodeViewData = mutableListOf(NodeViewData(
            bkgColor = Color.Red,
            borderColor = Color.Black,
            radius = 20.dp,
            radianVal = 0.0f
        )),
        requestViewData = mutableListOf(NodeViewData(
            bkgColor = Color.Red,
            borderColor = Color.Black,
            radius = 20.dp,
            radianVal = 180.0f
        ))
    )
}
