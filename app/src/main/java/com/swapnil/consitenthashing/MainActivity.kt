package com.swapnil.consitenthashing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapnil.consitenthashing.presentation.view.MyView
import com.swapnil.consitenthashing.ui.theme.ConsitentHashingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConsitentHashingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp.dp
                    val screenWidth = configuration.screenWidthDp.dp
                    MyView(
                        nodeViewData = viewModel.getNodeDataList().value,
                        requestViewData = viewModel.getRequestNodeDataList().value,
                        borderColor = Color.Red,
                        width = screenHeight.coerceAtMost(screenWidth) - 50.dp
                    )
                }
            }
        }
    }
}