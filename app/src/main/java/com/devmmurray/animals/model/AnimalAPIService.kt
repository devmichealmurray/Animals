package com.devmmurray.animals.model

import android.util.Log
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "AnimalAPIService"
private const val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

class AnimalAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey(): Single<ApiKey> {
        Log.d(TAG, ".getApiKey Called")
        return api.getAPIKey()
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        val getAnimals = api.getAnimals(key)
        Log.d(TAG, ".getAnimals Called $getAnimals")
        return getAnimals
    }
}