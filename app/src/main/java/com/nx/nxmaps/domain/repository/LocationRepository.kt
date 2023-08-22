package com.nx.nxmaps.domain.repository

import com.nx.nxmaps.domain.model.Location
import com.nx.nxmaps.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Locations = List<Location>
typealias LocationsResponse = Response<Locations>
typealias AddLocationResponse = Response<Boolean>
typealias DeleteLocationResponse = Response<Boolean>

interface LocationsRepository {

    fun getLocationsFromFirestore(): Flow<LocationsResponse>

    suspend fun addLocationToFirestore(lat: Double, lng: Double): AddLocationResponse

    suspend fun deleteLocationFromFirestore(locationId: String): DeleteLocationResponse

}