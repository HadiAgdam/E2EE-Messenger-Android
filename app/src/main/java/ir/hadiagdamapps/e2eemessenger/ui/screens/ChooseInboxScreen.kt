package ir.hadiagdamapps.e2eemessenger.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.hadiagdamapps.e2eemessenger.ui.components.InboxItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChooseInboxViewModel

@Composable
fun ChooseInboxScreen(viewModel: ChooseInboxViewModel) {


    Screen(
        title = "Choose Inbox",
        fabClick = {}
    ) {


        Column(
            modifier = Modifier.fillMaxSize()
        ) {


            for (i in 1..8) {
                InboxItem(
                    modifier = Modifier.clickable {  },
                    text = "asdasdsadhsakdjhjsakdhjksadhjkasasdasdsaasdasdasdd"
                ) {

                }
            }

        }

    }
}