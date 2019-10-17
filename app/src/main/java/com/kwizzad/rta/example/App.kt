package com.kwizzad.rta.example

import android.app.Application
import com.kwizzad.rta.KwizzadRta

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KwizzadRta.initialize(this)
    }
}