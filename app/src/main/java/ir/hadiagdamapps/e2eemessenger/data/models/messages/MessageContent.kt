package ir.hadiagdamapps.e2eemessenger.data.models.messages

import org.json.JSONObject

data class MessageContent(
    val senderPublicKey: String,
    val text: String
) {

    companion object {

        fun fromJson(json: JSONObject): MessageContent {
            return MessageContent(json.getString("sender_public_key"), json.getString("text"))
        }

    }

    fun toJson() = JSONObject().apply {
        put("sender_public_key", senderPublicKey)
        put("text", text)
    }
}
