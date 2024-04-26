package com.swapnil.consitenthashing

import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
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
            logData()
        }
    }

    private fun logData() {
        Log.d("TAG","Nodes: ${consistentHashing.getNodes()}")
        Log.d("TAG","Requests: ${consistentHashing.getRequests()}")
    }

    fun addRequest() {
        viewModelScope.launch(Dispatchers.Main) {
            // Adding a new request.
            consistentHashing.addRequest()
            recomputeRequests()
            logData()
        }
    }

    private fun recomputeNodes() {
        // Compute the list of node data list.
        nodeDataList.value = consistentHashing.getNodes().map {
            NodeViewData(
                bkgColor = hashToColorCodeConverter.convert(it.hashPosition),
                borderColor = Color.Black,
                radius = 20.dp,
                radianVal = (it.hashPosition.toFloat() / HASH_SIZE) * Math.PI.toFloat() * 2,
                text = it.title
            )
        }
    }

    private fun recomputeRequests() {
        // Compute the list of request data list.
        requestNodeDataList.value = consistentHashing.getRequests().map {
            NodeViewData(
                bkgColor = it.node?.let {
                        node -> hashToColorCodeConverter.convert(node.hashPosition)
                } ?: Color.Gray,
                borderColor = Color.DarkGray,
                radius = 15.dp,
                radianVal = (it.hashPosition.toFloat() / HASH_SIZE) * Math.PI.toFloat() * 2,
                text = it.title
            )
        }
    }


}