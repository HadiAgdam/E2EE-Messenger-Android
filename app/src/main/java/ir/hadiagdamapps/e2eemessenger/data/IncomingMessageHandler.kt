package ir.hadiagdamapps.e2eemessenger.data

import android.content.Context
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.database.InboxData
import ir.hadiagdamapps.e2eemessenger.data.database.LocalMessageData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.models.LocalMessageModel
import ir.hadiagdamapps.e2eemessenger.data.models.messages.IncomingMessage
import ir.hadiagdamapps.e2eemessenger.data.models.messages.MessageContent
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import retrofit2.Response
import java.security.PublicKey

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64


class IncomingMessageHandler(context: Context) {

    private val localMessageData = LocalMessageData(context)
    private val conversationData = ConversationData(context)


    // it should be called when got new messages from web polling
    fun gotNewMessages(
        response: Response<List<IncomingMessage>>, privateKey: String, inboxId: Long
    ): List<LocalMessageModel> {

        val result = ArrayList<LocalMessageModel>()

        if (response.isSuccessful) {
            for (message in response.body()!!) {
                try {
                    val key =
                        E2EEncryptor.decryptAESKeyWithPrivateKey(message.encryptionKey, privateKey)
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

                    val newMessageId = localMessageData.insertNewMessage(
                        conversationId, content.text, message.time.toLong(), false
                    )

                    conversationData.updateLastMessage(conversationId, newMessageId)

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
                    continue
                }
            }
        }

        return result
    }


}