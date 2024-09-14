package ir.hadiagdamapps.e2eemessenger

import android.app.Application
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

class MessengerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Security.addProvider(BouncyCastleProvider())

    }
}