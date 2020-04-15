package com.devmmurray.animals.model

import com.devmmurray.animals.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject


class AnimalAPIService {

    @Inject
    lateinit var api: AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> = api.getAPIKey()
    fun getAnimals(key: String): Single<List<Animal>> = api.getAnimals(key)

}