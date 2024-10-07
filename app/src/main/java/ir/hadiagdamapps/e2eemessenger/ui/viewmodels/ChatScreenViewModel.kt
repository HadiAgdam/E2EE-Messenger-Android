package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.database.LocalMessageData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.ChatMessageModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute
import javax.crypto.SecretKey

class ChatScreenViewModel : ViewModel() {

    private var localMessageData: LocalMessageData? = null
    private var conversationData: ConversationData? = null
    private var conversationId: Int? = null
    private var inboxId: Long? = null
    private var privateKey: String? = null
    private var senderPublicKey: String? = null
    private var aesKey: SecretKey? = null

    var chatBoxContent: String by mutableStateOf("")
        private set
    var conversationLabel: String by mutableStateOf("")
        private set

    private var _chats = mutableStateListOf<ChatMessageModel>()
    var chats: SnapshotStateList<ChatMessageModel> = _chats

    fun chatBoxTextChange(newText: String) {
        if (TextFormat.isValidMessage(newText)) chatBoxContent = newText
    }

    fun chatBoxSubmit() {
        TODO()
    }

    private fun loadMessages(conversationId: Int, aesKey: SecretKey) {
        _chats.clear()
        val newContent =
            (localMessageData ?: return).getConversationMessages(conversationId, aesKey)
        Log.e("new content count", newContent.size.toString())
        for (content in newContent) _chats.add(content)
    }

    fun init(arguments: ChatScreenRoute, context: Context) {
        localMessageData = LocalMessageData(context)
        conversationData = ConversationData(context)
        conversationId =
            conversationData!!.getConversationIdByPublicKey(publicKey = arguments.senderPublicKey)
        inboxId = arguments.inboxId
        privateKey = arguments.privateKey
        senderPublicKey = arguments.senderPublicKey
        aesKey = AesKeyGenerator.generateKey(arguments.aesKeyPin, arguments.aesKeySalt)
        conversationLabel = arguments.conversationLabel

        Log.e("conversation id", conversationId.toString())

        if (conversationId != null) loadMessages(conversationId!!, aesKey!!)
    }

}