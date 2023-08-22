package com.nx.nxmaps.data.repository

import com.google.firebase.firestore.CollectionReference
import com.nx.nxmaps.domain.model.Location
import com.nx.nxmaps.domain.model.Response.Failure
import com.nx.nxmaps.domain.model.Response.Success
import com.nx.nxmaps.domain.repository.AddLocationResponse
import com.nx.nxmaps.domain.repository.DeleteLocationResponse
import com.nx.nxmaps.domain.repository.LocationsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepositoryImpl @Inject constructor(
    private val locationsRef: CollectionReference
): LocationsRepository {

    override fun getLocationsFromFirestore() = callbackFlow {
        val snapshotListener = locationsRef.addSnapshotListener { snapshot, e ->
            val locationsResponse = if (snapshot != null) {
                val locations = snapshot.toObjects(Location::class.java)
                Success(locations)
            } else {
                Failure(e)
            }
            trySend(locationsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addLocationToFirestore(
        lat: Double,
        lng: Double
    ): AddLocationResponse {
        return try {
            val id = locationsRef.document().id
            val location = Location(
                id = id,
                lat = lat,
                lng = lng
            )
            locationsRef.document(id).set(location).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun deleteLocationFromFirestore(
        locationId: String
    ): DeleteLocationResponse {
        return try {
            locationsRef.document(locationId).delete().await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


}