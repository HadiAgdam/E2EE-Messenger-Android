package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChatScreenViewModel

@Composable
fun ChatScreen(viewModel: ChatScreenViewModel) {


    Screen(
        title = "Chat",
        fabClick = {}
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Chat Screen", color = Color.White)
        }
    }

}