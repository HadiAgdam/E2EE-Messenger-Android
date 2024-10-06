package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.ui.components.ConversationItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.BottomSheetMenu
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.ConfirmDeleteDialog
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.EditLabelBottomSheet
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.PinDialog
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(viewModel: InboxViewModel) {

    val state = rememberBottomSheetScaffoldState()

    LaunchedEffect(viewModel.isPolling) {
        if (viewModel.isPolling) viewModel.startPolling()
    }

    Screen(title = "Inbox", fabClick = viewModel::openNewConversationDialog, content = {
        LazyColumn {
            items(viewModel.conversations) { conversation: ConversationModel ->
                ConversationItem(
                    modifier = Modifier.clickable { viewModel.conversationClick(conversation) },
                    label = conversation.label,
                    detailsClick = { viewModel.conversationDetailsClick(conversation) },
                    lastMessageText = (if (conversation.lastMessage.sent) "You: " else "") + conversation.lastMessage.text,
                    timeText = TextFormat.timestampToText(conversation.lastMessage.timestamp),
                    unseenMessagesCount = conversation.unseenMessageCount
                )
            }
        }

        if (viewModel.pinDialogContent != null) PinDialog(
            pin = viewModel.pinDialogContent!!,
            onPinChanged = viewModel::pinChanged,
            error = viewModel.pinDialogError,
            okClick = viewModel::pinSubmitClick,
            dismiss = viewModel::dismissPinDialog
        )

        if (viewModel.isConfirmDeleteDialogOpen) ConfirmDeleteDialog(
            onOkClick = viewModel::okDeleteDialog, onCancelClick = viewModel::dismissDeleteDialog
        )

    }, scaffoldState = state, sheetContent = {

        if (viewModel.isOptionsMenuOpen) BottomSheetMenu(
            items = viewModel.menuOptions, onItemClick = viewModel::optionsMenuItemClick
        )


        if (viewModel.editLabelDialogText != null) EditLabelBottomSheet(
            text = viewModel.editLabelDialogText!!,
            onOkClick = viewModel::saveLabelClick,
            onCancelClick = viewModel::dismissEditLabelDialog,
            onTextChange = viewModel::labelChanged
        )

        if (viewModel.isNewConversationDialogOpen) BottomSheetMenu(
            items = viewModel.newConversationMenuOptions,
            onItemClick = viewModel::newConversationMenuItemClick
        )
    })
}