package com.nx.nxmaps.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nx.nxmaps.core.Constants.LOCATIONS
import com.nx.nxmaps.data.repository.LocationsRepositoryImpl
import com.nx.nxmaps.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLocationsRef() = Firebase.firestore.collection(LOCATIONS)

    @Provides
    fun provideLocationsRepository(
        locationsRef: CollectionReference
    ): LocationsRepository = LocationsRepositoryImpl(locationsRef)

}