package com.walmartlabs.task.model

import android.app.Application

/**
 * Created by Bipul on 25-08-2019.
 */
class WalmartApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }

    companion object {

        @get:Synchronized
        var instance: WalmartApplication? = null
            private set
    }
}