package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ir.hadiagdamapps.e2eemessenger.data.PendingMessageHandler
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.database.LocalMessageData
import ir.hadiagdamapps.e2eemessenger.data.database.PendingMessageData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.models.messages.ChatMessageModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.MessageContent
import ir.hadiagdamapps.e2eemessenger.data.models.messages.PendingPreviewMessageModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute
import javax.crypto.SecretKey

class ChatScreenViewModel : ViewModel() {

    private var localMessageData: LocalMessageData? = null
    private var conversationData: ConversationData? = null
    private var conversationId: Int? = null
    private var inboxPublicKey: String? = null
    private var privateKey: String? = null
    private var senderPublicKey: String? = null
    private var aesKey: SecretKey? = null
    private var pendingMessageHandler: PendingMessageHandler? = null

    var chatBoxContent: String by mutableStateOf("")
        private set
    var conversationLabel: String by mutableStateOf("")
        private set

    private var _chats = mutableStateListOf<ChatMessageModel>()
    var chats: SnapshotStateList<ChatMessageModel> = _chats

    private var _pendingMessages = mutableStateListOf<Pair<Int, String>>() // id , text
    var pendingMessages: SnapshotStateList<Pair<Int, String>> = _pendingMessages

    fun chatBoxTextChange(newText: String) {
        if (TextFormat.isValidMessage(newText)) chatBoxContent = newText
    }

    fun chatBoxSubmit() {

        val messageContent = MessageContent(
            senderPublicKey = inboxPublicKey!!, text = chatBoxContent
        )

        val key = AesKeyGenerator.generateKey()
        val encryptedKey = E2EEncryptor.encryptAESKeyWithPublicKey(
            key, E2EKeyGenerator.getPublicKeyFromString(senderPublicKey!!)
        )

        val message = AesEncryptor.encryptMessage(messageContent.toJson().toString(), key)

        val preview = AesEncryptor.encryptMessage(chatBoxContent, aesKey!!)


        val id = pendingMessageHandler!!.newPendingMessage(
            encryptedKey = encryptedKey,
            message = message.first,
            iv = message.second,
            previewText = preview.first,
            previewIv = preview.second
        )

        chatBoxContent = ""
    }

    private fun loadMessages(conversationId: Int, aesKey: SecretKey) {
        _chats.clear()
        val newContent =
            (localMessageData ?: return).getConversationMessages(conversationId, aesKey)
        Log.e("new content count", newContent.size.toString())
        for (content in newContent) _chats.add(content)
    }

    private fun loadPendingMessages() {
        _pendingMessages.clear()
        for (message in pendingMessageHandler?.getAllPendingPreviewMessages() ?: return) {
            val text = AesEncryptor.decryptMessage(message.encryptedMessage, aesKey!!, message.iv)
                ?: continue
            _pendingMessages.add(Pair(message.id, text))
        }
    }

    fun init(arguments: ChatScreenRoute, context: Context) {
        localMessageData = LocalMessageData(context)
        conversationData = ConversationData(context)
        conversationId =
            conversationData!!.getConversationIdByPublicKey(publicKey = arguments.senderPublicKey)
        inboxPublicKey = arguments.inboxPublicKey
        privateKey = arguments.privateKey
        senderPublicKey = arguments.senderPublicKey
        aesKey = AesKeyGenerator.generateKey(arguments.aesKeyPin, arguments.aesKeySalt)
        conversationLabel = arguments.conversationLabel
        pendingMessageHandler = object : PendingMessageHandler(
            PendingMessageData(context), inboxPublicKey!!, senderPublicKey!!
        ) {
            override fun messageSent(pendingMessageId: Int) {
                for (message in _pendingMessages) if (message.first == pendingMessageId) _pendingMessages.remove(
                    message
                )
            }

            override fun gotNewPendingMessage(pendingPreviewMessageModel: PendingPreviewMessageModel) {
                _pendingMessages.add(
                    Pair(
                        pendingPreviewMessageModel.id, AesEncryptor.decryptMessage(
                            pendingPreviewMessageModel.encryptedMessage,
                            aesKey!!,
                            pendingPreviewMessageModel.iv
                        ) ?: return
                    )
                )
            }
        }

        Log.e("conversation id", conversationId.toString())

        if (conversationId != null) loadMessages(conversationId!!, aesKey!!)
        loadPendingMessages()
    }

    fun cancelSending(pendingMessageId: Int) {
        pendingMessageHandler?.cancelSending(pendingMessageId)
        for (message in _pendingMessages) if (message.first == pendingMessageId) _pendingMessages.remove(
            message
        )
    }

}