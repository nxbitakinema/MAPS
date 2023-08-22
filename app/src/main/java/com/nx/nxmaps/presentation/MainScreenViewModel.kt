package com.nx.nxmaps.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nx.nxmaps.domain.model.Response.Loading
import com.nx.nxmaps.domain.model.Response.Success
import com.nx.nxmaps.domain.repository.AddLocationResponse
import com.nx.nxmaps.domain.repository.DeleteLocationResponse
import com.nx.nxmaps.domain.repository.LocationsRepository
import com.nx.nxmaps.domain.repository.LocationsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: LocationsRepository
): ViewModel() {

    var locationsResponse by mutableStateOf<LocationsResponse>(Loading)
        private set

    var addLocationResponse by mutableStateOf<AddLocationResponse>(Success(false))
        private set

    var deleteLocationResponse by mutableStateOf<DeleteLocationResponse>(Success(false))
        private set

    init {
        getLocations()
    }

    private fun getLocations() = viewModelScope.launch {
        repo.getLocationsFromFirestore().collect { response ->
            locationsResponse = response
        }
    }

    fun addLocation(
        lat: Double,
        lng: Double
    ) = viewModelScope.launch {
        addLocationResponse = Loading
        addLocationResponse = repo.addLocationToFirestore(lat, lng)
    }

    fun deleteLocation(
        locationId: String
    ) = viewModelScope.launch {
        deleteLocationResponse = Loading
        deleteLocationResponse = repo.deleteLocationFromFirestore(locationId)
    }



}