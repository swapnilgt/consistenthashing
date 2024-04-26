package com.swapnil.consitenthashing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnil.consitenthashing.domain.ConsistentHashing
import com.swapnil.consitenthashing.domain.HASH_SIZE
import com.swapnil.consitenthashing.presentation.pojo.NodeViewData
import com.swapnil.consitenthashing.presentation.usecase.HashToColorCodeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val consistentHashing: ConsistentHashing,
    private val hashToColorCodeConverter: HashToColorCodeConverter
): ViewModel() {

    private val nodeDataList: MutableState<List<NodeViewData>> = mutableStateOf(emptyList())
    private val requestNodeDataList: MutableState<List<NodeViewData>> = mutableStateOf(emptyList())

    fun getNodeDataList(): MutableState<List<NodeViewData>> = nodeDataList
    fun getRequestNodeDataList(): MutableState<List<NodeViewData>> = requestNodeDataList


    fun addNode() {
        viewModelScope.launch(Dispatchers.Main) {
            // Adding a new node.
            consistentHashing.addNode()
            recomputeNodes()
            recomputeRequests()
        }
    }

    fun addRequest() {
        viewModelScope.launch(Dispatchers.Main) {
            // Adding a new request.
            consistentHashing.addRequest()
            recomputeRequests()
        }
    }

    private fun recomputeNodes() {
        // Compute the list of node data list.
        nodeDataList.value = consistentHashing.getNodes().map {
            NodeViewData(
                bkgColor = hashToColorCodeConverter.convert(it.hashCode()),
                borderColor = Color.Black,
                radius = 20.dp,
                radianVal = (it.hashCode().toFloat() / HASH_SIZE) * Math.PI.toFloat() * 2
            )
        }
    }

    private fun recomputeRequests() {
        // Compute the list of request data list.
        requestNodeDataList.value = consistentHashing.getRequests().map {
            NodeViewData(
                bkgColor = it.node?.let {
                        node -> hashToColorCodeConverter.convert(node.hashCode())
                } ?: Color.Gray,
                borderColor = Color.DarkGray,
                radius = 15.dp,
                radianVal = (it.hashCode().toFloat() / HASH_SIZE) * Math.PI.toFloat() * 2
            )
        }
    }


}