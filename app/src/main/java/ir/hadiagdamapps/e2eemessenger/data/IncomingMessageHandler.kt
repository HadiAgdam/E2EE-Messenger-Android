package ir.hadiagdamapps.e2eemessenger.data

import android.content.Context
import android.util.Log
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.database.LocalMessageData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.models.messages.LocalMessageModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.IncomingMessage
import ir.hadiagdamapps.e2eemessenger.data.models.messages.MessageContent
import org.json.JSONObject
import retrofit2.Response

import javax.crypto.SecretKey


class IncomingMessageHandler(context: Context) {

    private val localMessageData = LocalMessageData(context)
    private val conversationData = ConversationData(context)


    // it should be called when got new messages from web polling
    fun gotNewMessages(
        response: Response<List<IncomingMessage>>,
        privateKey: String,
        inboxId: Long,
        aesKey: SecretKey
    ): List<LocalMessageModel> {

        val result = ArrayList<LocalMessageModel>()
        Log.e("response body length", response.body()!!.size.toString())
        if (response.isSuccessful) {
            for (message in response.body()!!) {
                try {
                    Log.e("encryption key", message.encryptionKey)

                    val key =
                        E2EEncryptor.decryptAESKeyWithPrivateKey(
                            message.encryptionKey,
                            E2EKeyGenerator.getPrivateKeyFromString(privateKey)
                        )

                    val messageData = JSONObject(
                        AesEncryptor.decryptMessage(message.encryptedMessage, key, message.iv)
                            ?: throw Exception("Text format invalid")
                    )

                    val content = MessageContent.fromJson(messageData)

                    val conversationId =
                        conversationData.getConversationIdByPublicKey(content.senderPublicKey)
                            ?: conversationData.newConversation(
                                inboxId = inboxId, publicKey = content.senderPublicKey
                            )
                    val encryptedMessage = AesEncryptor.encryptMessage(content.text, aesKey)
                    val newMessageId = localMessageData.insertNewMessage(
                        conversationId, encryptedMessage.first, message.time.toLong(), false, encryptedMessage.second
                    )


                    conversationData.updateLastMessage(conversationId, newMessageId)
                    conversationData.incrementUnseenCount(conversationId)

                    result.add(
                        LocalMessageModel(
                            messageId = newMessageId,
                            conversationId = conversationId,
                            text = content.text,
                            timestamp = message.time.toLong(),
                            sent = false,
                            iv = message.iv
                        )
                    )

                } catch (ex: Exception) {
                    throw ex
                }
            }
        }

        return result
    }


}