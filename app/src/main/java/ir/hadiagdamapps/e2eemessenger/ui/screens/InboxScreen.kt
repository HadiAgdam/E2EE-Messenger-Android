package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.ui.components.ConversationItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.dialog.PinDialog
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.InboxViewModel

@Composable
fun InboxScreen(viewModel: InboxViewModel) {
    Screen(
        title = "Inbox",
        fabClick = viewModel::newConversation
    ) {
        LazyColumn {
            items(viewModel.conversations) { conversation: ConversationModel ->
                ConversationItem(
                    modifier = Modifier.clickable { viewModel.conversationClick(conversation) },
                    label = conversation.label,
                    detailsClick = { viewModel.conversationClick(conversation) },
                    lastMessageText = (if (conversation.lastMessage.sent) "You: " else "") + conversation.lastMessage.text,
                    timeText = TextFormat.timestampToText(conversation.lastMessage.timestamp)
                )
            }
        }


        if (viewModel.pin != null) PinDialog(
            pin = viewModel.pin!!,
            onPinChanged = viewModel::pinChanged,
            error = viewModel.pinDialogError,
            okClick = viewModel::pinSubmitClick,
            dismiss = viewModel::dismiss
        )
    }
}