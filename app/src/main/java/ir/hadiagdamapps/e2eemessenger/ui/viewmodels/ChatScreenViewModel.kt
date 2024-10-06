package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.ChatMessageModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute

class ChatScreenViewModel : ViewModel() {

    var conversationLabel: String by mutableStateOf("")
        private set

    private val _chats = mutableStateListOf<ChatMessageModel>()
    val chats: SnapshotStateList<ChatMessageModel> = _chats

    var chatBoxContent: String by mutableStateOf("")

    fun chatBoxTextChange(newText: String) {
        if (TextFormat.isValidMessage(newText))
            chatBoxContent = newText
    }

    fun init(arguments: ChatScreenRoute) {

    }

}