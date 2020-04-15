package com.devmmurray.animals

import android.app.Application
import com.devmmurray.animals.di.PrefsModule
import com.devmmurray.animals.util.SharedPreferencesHelper

class PrefsModuleTest(private val mockPrefs: SharedPreferencesHelper): PrefsModule() {
    override fun providesSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}

