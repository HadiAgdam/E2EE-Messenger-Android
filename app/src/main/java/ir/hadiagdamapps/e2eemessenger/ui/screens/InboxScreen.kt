package ir.hadiagdamapps.e2eemessenger.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.ui.components.ConversationItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.BottomSheetMenu
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.ConfirmDeleteDialog
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.EditLabelBottomSheet
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.PinDialog
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(viewModel: InboxViewModel) {

    val state =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    LaunchedEffect(viewModel.isPolling) {
        if (viewModel.isPolling) viewModel.startPolling()
    }

//    LaunchedEffect(viewModel.isNewConversationDialogOpen || viewModel.showEditLabelDialog || viewModel.isOptionsMenuOpen) {
//        if (viewModel.isNewConversationDialogOpen)
//            scope.launch {
//                state.show()
//            }
//        else state.hide()
//    }

    LaunchedEffect(viewModel.isOptionsMenuOpen) {
        if (viewModel.isOptionsMenuOpen)
            scope.launch {
                state.show()
            }
        else state.hide()
    }
    LaunchedEffect(viewModel.showEditLabelDialog) {
        if (viewModel.showEditLabelDialog)
            scope.launch {
                state.show()
            }
        else state.hide()
    }
    LaunchedEffect(viewModel.isNewConversationDialogOpen) {
        if (viewModel.isNewConversationDialogOpen)
            scope.launch {
                state.show()
            }
        else state.hide()
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

        if (viewModel.showPinDialog) PinDialog(
            pin = viewModel.pinDialogContent,
            onPinChanged = viewModel::pinChanged,
            error = viewModel.pinDialogError,
            okClick = viewModel::pinSubmitClick,
            dismiss = viewModel::dismissPinDialog
        )

        if (viewModel.isConfirmDeleteDialogOpen) ConfirmDeleteDialog(
            onOkClick = viewModel::okDeleteDialog, onCancelClick = viewModel::dismissDeleteDialog
        )

        if (viewModel.isOptionsMenuOpen || viewModel.showEditLabelDialog || viewModel.isNewConversationDialogOpen)
            ModalBottomSheet(onDismissRequest = {
                Log.e("dismiss request", "")
                viewModel.dismissBottomSheets()
            }, sheetState = state, dragHandle = null, shape = RectangleShape) {

                if (viewModel.isOptionsMenuOpen) BottomSheetMenu(
                    items = viewModel.menuOptions, onItemClick = viewModel::optionsMenuItemClick
                )

                if (viewModel.showEditLabelDialog) EditLabelBottomSheet(
                    text = viewModel.editLabelDialogText,
                    onOkClick = viewModel::saveLabelClick,
                    onCancelClick = viewModel::dismissEditLabelDialog,
                    onTextChange = viewModel::labelChanged
                )

                if (viewModel.isNewConversationDialogOpen) BottomSheetMenu(
                    items = viewModel.newConversationMenuOptions,
                    onItemClick = viewModel::newConversationMenuItemClick
                )
            }


    }
    )

}