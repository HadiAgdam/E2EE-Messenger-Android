package ir.hadiagdamapps.e2eemessenger.data

import android.util.Log
import ir.hadiagdamapps.e2eemessenger.data.database.PendingMessageData
import ir.hadiagdamapps.e2eemessenger.data.models.messages.OutgoingMessage
import ir.hadiagdamapps.e2eemessenger.data.models.messages.PendingMessageModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.PendingPreviewMessageModel
import ir.hadiagdamapps.e2eemessenger.data.network.ApiService
import kotlinx.coroutines.delay

abstract class PendingMessageHandler
    (
    private val pendingMessageData: PendingMessageData,
    private val inboxPublicKey: String,
    private val recipientPublicKey: String,
    private val apiService: ApiService
) {

    private var pendingMessages = ArrayList<PendingMessageModel>()
    private var sending: Boolean = false


    fun stopSendingMessages() {
        sending = false
    }

    suspend fun startSendingMessages() {
        sending = true
        while (sending) {

            try {
                pendingMessages.toList().forEach {
                    if (apiService.newMessage(it.toOutgoingMessage()).isSuccessful) {
                        pendingMessages.remove(it)
                        messageSent(it.messageId)
                    }
                }
            } catch (ex: Exception) {
                Log.e("error", ex.toString())
            }


            delay(500)
        }
    }

    private fun registerToSender(
        pendingMessage: PendingMessageModel
    ) {
        pendingMessages.add(pendingMessage)
    }

    fun newPendingMessage(
        encryptedKey: String,
        message: String,
        iv: String,
        previewText: String,
        previewIv: String
    ) =
        pendingMessageData.newPendingMessage(
            inboxPublicKey = inboxPublicKey,
            recipientPublicKey = recipientPublicKey,
            encryptedKey = encryptedKey,
            message = message,
            iv = iv,
            previewText = previewText,
            previewIv = previewIv
        ).apply {
            registerToSender(
                PendingMessageModel(
                    messageId = this,
                    encryptedKey = encryptedKey,
                    message = message,
                    iv = iv,
                    inboxPublicKey = inboxPublicKey,
                    recipientPublicKey = recipientPublicKey,
                    previewText = previewText,
                    previewIv = previewIv
                )
            )
            gotNewPendingMessage(
                PendingPreviewMessageModel(
                    id = 0,
                    encryptedMessage = previewText,
                    iv = previewIv
                )
            )
        }


    fun getAllPendingPreviewMessages(): List<PendingPreviewMessageModel> {
        pendingMessages = pendingMessageData.getPendingMessages(
            inboxPublicKey = inboxPublicKey,
            recipientPublicKey = recipientPublicKey
        )

        return ArrayList<PendingPreviewMessageModel>().apply {
            for (message in pendingMessages)
                add(message.toPendingPreviewMessageModel())
        }
    }

    fun cancelSending(pendingMessageId: Int) {
        pendingMessages.forEach {
            if (it.messageId == pendingMessageId) pendingMessages.remove(it)
        }
    }

    abstract fun gotNewPendingMessage(pendingPreviewMessageModel: PendingPreviewMessageModel)

    abstract fun messageSent(pendingMessageId: Int)
}