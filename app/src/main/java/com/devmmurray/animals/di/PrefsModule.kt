package com.devmmurray.animals.di

import android.app.Application
import com.devmmurray.animals.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class PrefsModule {
    @Provides
    @Singleton
    open fun providesSharedPreferences(app: Application):
            SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }
}