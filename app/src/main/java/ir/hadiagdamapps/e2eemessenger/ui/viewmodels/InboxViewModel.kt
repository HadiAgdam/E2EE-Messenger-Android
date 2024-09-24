package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.TextFormat.PIN_LENGTH
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import javax.crypto.SecretKey


class InboxViewModel : ViewModel() {

    private var inbox: InboxModel? = null
    private var navController: NavHostController? = null
    private var data: ConversationData? = null

    private val _conversations = mutableStateListOf<ConversationModel>()
    val conversations: SnapshotStateList<ConversationModel> = _conversations

    var pin: String? by mutableStateOf("")
        private set

    var pinDialogError: String? by mutableStateOf(null)
        private set

    var isOptionsMenuOpen by mutableStateOf(false)
        private set

    var confirmDeleteDialogContent: ConversationModel? by mutableStateOf(null)
        private set

    var sharePublicKeyDialogContent: InboxDialogModel? by mutableStateOf(null)
        private set

    var editLabelDialogText: String? by mutableStateOf(null)
        private set


    // TODO user should enter pin to generate private key first

    // Dear future me, please forgive me. I know this is wrong but I don't know any better way.
    fun init(navController: NavHostController, inbox: InboxScreenRoute, context: Context) {
        this.inbox = inbox
        this.navController = navController
        displayWrongPinMessage = {
            // WTF is going on here ?
            Toast.makeText(context, "wrong pin", Toast.LENGTH_SHORT).show()
        }
        this.data = ConversationData(context)
    }


    fun pinChanged(newPin: String) {
        if (newPin.length < PIN_LENGTH + 1) {
            pin = newPin
            pinDialogError = null
        }
    }// I think I should create a handler to avoid duplication


    fun pinSubmitClick() =
        if (TextFormat.isValidPin(pin)) {
            inbox?.let {
                privateKey = AesEncryptor.decryptMessage(
                    it.encryptedPrivateKey,
                    AesKeyGenerator.generateKey(pin!!, it.salt!!),
                    it.iv!!
                )
                loadMessages()
            }
        } else pinDialogError = "invalid pin format"


    private fun loadMessages() {
        if (privateKey != null) {
            aesKey = AesKeyGenerator.generateKey(pin!!, inbox?.salt!!)

            inbox?.inboxId?.let { data?.loadConversations(inboxId = it) }

            val list = data?.loadConversations(inboxId = inbox?.inboxId!!)!!

            _conversations.clear()
            for (item in list) {
                val message = item.lastMessage.copy(text = AesEncryptor.decryptMessage(
                    item.lastMessage.text,
                    aesKey!!,
                    inbox?.iv!!
                )!!)

                _conversations.add(item.copy(lastMessage = message))
            }

        } else {
            displayWrongPinMessage?.invoke()

            back()
        }
    }

    private fun back() {
        navController?.popBackStack()
    }


    fun newConversation() {

    }

    fun dismiss() {
        pin = null
        back()
    }

    fun conversationClick(conversation: ConversationModel) {

    }

    fun newConversation() {

    }

    // ---------------------------------------------------------------------------------------------

    fun conversationDetailsClick(conversation: ConversationModel) {

    }

    fun optionsMenuItemClick(item: MenuItem) {
        when (item) {
            menuOptions[0] -> TODO("copy")
            menuOptions[1] -> TODO("edit")
            menuOptions[2] -> TODO("delete")
        }

    }

    // ---------------------------------------------------------------------------------------------

    fun labelChanged(newLabel: String) {
        if (isValidLabel(newLabel))
            editLabelDialogText = newLabel
    }

    fun saveLabelClick() {

    }

    fun dismissEditLabelDialog() {

    }

    // ---------------------------------------------------------------------------------------------

    fun dismissDeleteDialog() {

    }

    fun okDeleteDialog() {

    }

    // ---------------------------------------------------------------------------------------------




}