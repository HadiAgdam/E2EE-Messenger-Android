package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.PinDialog
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel

@Composable
fun InboxScreen(viewModel: InboxViewModel) {
    Screen(
        title = "Inbox",
        fabClick = viewModel::newChat
    ) {
        LazyColumn {

        }


        if (viewModel.pin != null) PinDialog(
            pin = viewModel.pin!!,
            onPinChanged = viewModel::pinChanged,
            error = viewModel.pinDialogError,
            okClick = viewModel::pinSubmitClick,
            dismiss = {}/* really ? */)
    }
}