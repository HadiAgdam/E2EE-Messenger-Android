package ir.hadiagdamapps.e2eemessenger

import android.app.Application
import ir.hadiagdamapps.e2eemessenger.data.network.Network

class MessengerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Network.initQueue(this)
    }
}