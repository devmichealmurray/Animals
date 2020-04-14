package com.devmmurray.animals.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmmurray.animals.model.Animal
import com.devmmurray.animals.model.AnimalAPIService
import com.devmmurray.animals.model.ApiKey
import com.devmmurray.animals.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "ListViewModel"

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    private val disposable = CompositeDisposable()
    private val apiService = AnimalAPIService()
    private val prefs = SharedPreferencesHelper(getApplication())
    private var invalidApiKey = false

    fun refresh() {
        Log.d(TAG, ".refresh Called")
        loading.value = true
        invalidApiKey = false
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    private fun getKey() {
        Log.d(TAG, ".getKey Called")
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        Log.d(TAG, "OnSuccess Called ${key.key}")
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            Log.d(TAG, "Key Received! ${key.key}")
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "getKey.onError Called ${e.message}")
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        Log.d(TAG, "getAnimals Called -> $key")
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}