package net.pubnative.example

import android.app.Application
import net.pubnative.hybidx.HyBidX

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        HyBidX.initialize(this)
    }
}