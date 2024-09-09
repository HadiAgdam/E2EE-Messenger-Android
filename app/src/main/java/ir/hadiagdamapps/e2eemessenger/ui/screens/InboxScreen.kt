package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel

@Composable
fun InboxScreen(viewModel: InboxViewModel) {
    Screen(
        title = "Inbox",
        fabClick = {}
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Inbox Screen", color = Color.White)
        }
    }
}