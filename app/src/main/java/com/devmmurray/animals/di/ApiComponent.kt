package com.devmmurray.animals.di

import com.devmmurray.animals.model.AnimalAPIService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalAPIService)

}