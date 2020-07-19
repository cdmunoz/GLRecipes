package co.cdmunoz.glrecipes

import android.app.Application
import timber.log.Timber

class RecipesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}