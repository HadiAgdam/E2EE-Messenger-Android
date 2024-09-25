package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.Context
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel

class ConversationData (private val context: Context) {

    fun loadConversations(inboxId: Long): List<ConversationModel> {
        // TODO("Select from conversations where inboxId matches")
        return listOf()
    }


    fun getSenderPublicKey(conversationId: Int): String {
        TODO()
    }

    fun updateLabel(conversationId: Int, newLabel: String) {
        TODO()
    }

    fun delete(conversationId: Int) {
        TODO()
    }

}