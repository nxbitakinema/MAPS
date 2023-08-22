package com.nx.nxmaps.components_wait_test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.DragState
import com.google.maps.android.compose.MarkerState

@Composable
fun DebugView(
    cameraPositionState: CameraPositionState,
    markerState: MarkerState
) {
    val moving = if (cameraPositionState.isMoving) "moving" else "not moving"
    val dragging = if (markerState.dragState == DragState.DRAG) "dragging" else "not dragging"

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(14.dp)
    ) {
        Text(
            text = "camera status : $moving"
        )
        Text(
            text = "camera position : ${cameraPositionState.position}"
        )
        Text(
            text = "marker status : $dragging"
        )
        Text(
            text = "marker position : ${markerState.position}"
        )
    }
}

