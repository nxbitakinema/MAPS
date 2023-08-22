package com.nx.nxmaps.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nx.nxmaps.components.ProgressBar
import com.nx.nxmaps.core.Constants.SNIPPET
import com.nx.nxmaps.core.Constants.ZOOM
import com.nx.nxmaps.domain.model.Response.Failure
import com.nx.nxmaps.domain.model.Response.Loading
import com.nx.nxmaps.domain.model.Response.Success

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val cityCenter = LatLng(44.1767, 28.6518)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityCenter, ZOOM) }

    when(
        val locationsResponse = viewModel.locationsResponse
    ) {
        is Loading -> ProgressBar()
        is Success -> GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLongClick = { latLng ->
                viewModel.addLocation(latLng.latitude, latLng.longitude)
            },
            content = {
                locationsResponse.data.forEach { location ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(location.lat!!, location.lng!!)
                        ),
                        title = "${location.lat}, ${location.lng}",
                        snippet = SNIPPET,
                        onInfoWindowLongClick = { marker ->
                            marker.hideInfoWindow()
                            viewModel.deleteLocation(location.id!!)
                        }
                    )
                }
            }
        )
        is Failure -> print(locationsResponse.e)
    }
}