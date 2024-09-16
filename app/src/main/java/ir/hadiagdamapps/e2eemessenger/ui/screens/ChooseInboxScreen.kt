package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.InboxDetailsDialog
import ir.hadiagdamapps.e2eemessenger.ui.components.InboxItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.PinDialog
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChooseInboxViewModel

@Composable
fun ChooseInboxScreen(viewModel: ChooseInboxViewModel) {


    Screen(
        title = "Choose Inbox", fabClick = viewModel::newInbox
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(viewModel.inboxes) { inbox: InboxModel ->
                InboxItem(text = inbox.label, modifier = Modifier.clickable {
                    viewModel.inboxClick(inbox)
                }, moreClick = {
                    viewModel.showInboxDialog(inbox)
                })
            }


        }


        if (viewModel.inboxDialog != null) InboxDetailsDialog(
            model = viewModel.inboxDialog!!,
            dismiss = viewModel::inboxDialogDismiss,
            copyPublicKey = viewModel::inboxDialogCopyPublicKey,
            deleteInbox = viewModel::deleteInbox,
            labelValueChange = viewModel::inboxDialogLabelChange
        )

        if (viewModel.pin != null) PinDialog(
            pin = viewModel.pin!!,
            onPinChanged = viewModel::pinChanged,
            error = viewModel.pinDialogError,
            dismiss = viewModel::pinDialogDismiss,
            okClick = viewModel::pinDialogSubmit
        )

    }
}