package me.project.marvelapp.presentation

import android.app.Application
import me.project.marvelapp.data.di.*
import me.project.marvelapp.presentation.ui.detail.DetailViewModel
import me.project.marvelapp.presentation.ui.list.ListCharacterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()



        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModelModule(),
                    useCasesModule(),
                    marvelRepositoryModule(),
                    remoteModule(),
                    localModule()
                )
            )
        }
    }
}
