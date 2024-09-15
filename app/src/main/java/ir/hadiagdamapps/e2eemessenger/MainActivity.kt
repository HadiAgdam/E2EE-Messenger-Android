package ir.hadiagdamapps.e2eemessenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import ir.hadiagdamapps.e2eemessenger.ui.navigation.ChatAppNavHost
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            E2EEMessengerTheme {
                val navHostController = rememberNavController()
                ChatAppNavHost(navController = navHostController, this)
            }
        }
    }
}
