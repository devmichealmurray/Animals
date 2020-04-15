package com.devmmurray.animals

import com.devmmurray.animals.di.ApiModule
import com.devmmurray.animals.model.AnimalAPIService

class ApiModuleTest(private val mockService: AnimalAPIService): ApiModule() {
    override fun provideAnimalApiService(): AnimalAPIService {
        return mockService
    }
}
