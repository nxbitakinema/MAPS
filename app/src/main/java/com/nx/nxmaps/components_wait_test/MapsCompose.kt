package com.nx.nxmaps.components_wait_test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.nx.nxmaps.R

// config latitude + zoom + show label
@Composable
fun MapsCompose1() {

    val asakusa = LatLng(35.7170638, 139.7884502)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(asakusa, 30f) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = asakusa),
            title = "asakusa",
            snippet = "marker in asakusa"
        )
    }
}


// config latitude + zoom + show label + [ marker icon ] enable drag + change color
@Composable
fun MapsCompose2() {

    val asakusa = LatLng(35.7170638, 139.7884502)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(asakusa, 30f) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = rememberMarkerState(position = asakusa),
            draggable = true,
            title = "Marker 1",
            snippet = "Marker in Singapore",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        )
    }
}

// open maps ui [ zoom ] normal on/off
@Composable
fun MapsCompose3() {

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        uiSettings = uiSettings.copy(zoomControlsEnabled = true)

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings
        )
    }
}

// on/off maps ui by [ Switch ]
@Composable
fun MapsCompose4() {

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings
        )
        Column(
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Switch(
                checked = uiSettings.scrollGesturesEnabled,
                onCheckedChange = {
                    uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                }
            )
            Switch(
                checked = uiSettings.rotationGesturesEnabled,
                onCheckedChange = {
                    uiSettings = uiSettings.copy(rotationGesturesEnabled = it)
                }
            )
            Switch(
                checked = uiSettings.zoomGesturesEnabled,
                onCheckedChange = {
                    uiSettings = uiSettings.copy(zoomGesturesEnabled = it)
                }
            )
        }
    }
}


// marker info windoww
@Composable
fun MapsCompose5() {

    val asakusa = LatLng(35.7170638, 139.7884502)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(asakusa, 16f) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState
        ) {

            val icon = pinIcon(LocalContext.current, R.drawable.pin)

            MarkerInfoWindow(
                state = MarkerState(position = asakusa),
                icon = icon
            ) { marker ->
                Card(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .height(220.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.map2),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ASAKUSA",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "detail",
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}