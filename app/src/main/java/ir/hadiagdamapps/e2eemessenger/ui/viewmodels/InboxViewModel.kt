package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.data.Clipboard
import ir.hadiagdamapps.e2eemessenger.data.IncomingMessageHandler
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.TextFormat.PIN_LENGTH
import ir.hadiagdamapps.e2eemessenger.data.TextFormat.isValidLabel
import ir.hadiagdamapps.e2eemessenger.data.database.ConversationData
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.models.MenuItem
import ir.hadiagdamapps.e2eemessenger.data.network.ApiService
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.ChatScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.crypto.SecretKey


class InboxViewModel : ViewModel() {

    private var incomingMessageHandler: IncomingMessageHandler? = null
    private var apiService: ApiService? = null
    private var displayWrongPinMessage: (() -> Unit)? = null
    private var aesKey: SecretKey? = null
    private var privateKey: String? = null
    private var inbox: InboxModel? = null
    private var navController: NavHostController? = null
    private var data: ConversationData? = null
    val menuOptions = listOf(
        MenuItem("copy public key", R.drawable.copy_icon),
        MenuItem("edit label", R.drawable.edit_icon),
        MenuItem("delete", R.drawable.delete_icon)
    )
    val newConversationMenuOptions = listOf(
        MenuItem("get from clipboard", R.drawable.copy_icon),
        MenuItem("scan QR code", R.drawable.scan_qr_code_icon)
    )

    private val _conversations = mutableStateListOf<ConversationModel>()
    val conversations: SnapshotStateList<ConversationModel> = _conversations

    var pinDialogContent: String by mutableStateOf("")
        private set

    var showPinDialog: Boolean by mutableStateOf(true)
        private set

    private var pin: String? by mutableStateOf(null)


    var pinDialogError: String? by mutableStateOf(null)
        private set


    private var optionsMenuContent: ConversationModel? by mutableStateOf(null)

    var isOptionsMenuOpen by mutableStateOf(false)
        private set

    var isConfirmDeleteDialogOpen by mutableStateOf(false)
        private set

    var editLabelDialogText: String by mutableStateOf("")
        private set

    var showEditLabelDialog: Boolean by mutableStateOf(false)
        private set

    var isNewConversationDialogOpen by mutableStateOf(false)
        private set

    var isPolling by mutableStateOf(false)
        private set


    // TODO user should enter pin to generate private key first

    // Dear future me, please forgive me. I know this is wrong but I don't know any better way.
    fun init(
        navController: NavHostController,
        inbox: InboxScreenRoute,
        context: Context,
        apiService: ApiService
    ) {
        this.inbox = inbox
        this.navController = navController
        displayWrongPinMessage = {
            // WTF is going on here ?
            Toast.makeText(context, "wrong pin", Toast.LENGTH_SHORT).show()
        }
        this.data = ConversationData(context)
        this.apiService = apiService
        this.incomingMessageHandler = IncomingMessageHandler(context)
    }


    private fun loadConversations() {
        if (privateKey != null) {
            aesKey = AesKeyGenerator.generateKey(pin!!, inbox?.salt!!)

            inbox?.inboxId?.let { data?.loadConversations(inboxId = it) }

            val list = data?.loadConversations(inboxId = inbox?.inboxId!!)!!

            _conversations.clear()
            for (item in list) {
                Log.e("unseen message count", item.unseenMessageCount.toString())

                val message = item.lastMessage.copy(
                    text = AesEncryptor.decryptMessage(
                        item.lastMessage.text, aesKey!!, item.lastMessage.iv
                    )!!
                )

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

    // ---------------------------------------------------------------------------------------------

    fun pinChanged(newPin: String) {
        if (newPin.length < PIN_LENGTH + 1) {
            pinDialogContent = newPin
            pinDialogError = null
        }
    }// I think I should create a handler to avoid duplication


    fun pinSubmitClick() = if (TextFormat.isValidPin(pinDialogContent)) {
        inbox?.let {
            privateKey = AesEncryptor.decryptMessage(
                it.encryptedPrivateKey,
                AesKeyGenerator.generateKey(pinDialogContent, it.salt!!),
                it.iv!!
            )
            pin = pinDialogContent
            pinDialogContent = ""
            showPinDialog = false
            loadConversations()
            isPolling = true
        }
    } else pinDialogError = "invalid pin format"


    fun dismissPinDialog() {
        pinDialogContent = ""
        showPinDialog = false
        back()
    }

    // ---------------------------------------------------------------------------------------------

    fun conversationClick(conversation: ConversationModel) {
        openConversationScreen(conversation.senderPublicKey)
    }

    // ---------------------------------------------------------------------------------------------

    fun conversationDetailsClick(conversation: ConversationModel) {
        optionsMenuContent = conversation
        isOptionsMenuOpen = true
    }

    fun optionsMenuItemClick(item: MenuItem) {
        when (item) {

            menuOptions[0] -> {
                Clipboard.copy((optionsMenuContent ?: return).senderPublicKey)
                optionsMenuContent = null
            }

            menuOptions[1] ->{ editLabelDialogText = optionsMenuContent?.label!!
            showEditLabelDialog = true}

            menuOptions[2] -> isConfirmDeleteDialogOpen = true
        }
        isOptionsMenuOpen = false
    }

    // ---------------------------------------------------------------------------------------------

    fun labelChanged(newLabel: String) {
        if (isValidLabel(newLabel)) editLabelDialogText = newLabel
        optionsMenuContent = null
    }

    fun saveLabelClick() {
        data?.updateLabel(optionsMenuContent!!.id, editLabelDialogText)
        optionsMenuContent = null
    }

    fun dismissEditLabelDialog() {
        editLabelDialogText = ""
        showEditLabelDialog = false
        optionsMenuContent = null
    }

    // ---------------------------------------------------------------------------------------------

    fun dismissDeleteDialog() {
        isConfirmDeleteDialogOpen = false
        optionsMenuContent = null
    }

    fun okDeleteDialog() {
        data?.delete(optionsMenuContent!!.id)
        optionsMenuContent = null
    }

    // ---------------------------------------------------------------------------------------------


    fun openNewConversationDialog() {
        isNewConversationDialogOpen = true
    }

    fun newConversationMenuItemClick(item: MenuItem) {
        when (item) {
            newConversationMenuOptions[0] -> newConversation(Clipboard.readClipboard())
            newConversationMenuOptions[1] -> TODO("get public key from Qr code and create a new conversation")
        }
        isOptionsMenuOpen = false
    }


    private fun newConversation(publicKey: String) {
        if (TextFormat.isValidPublicKey(publicKey))
        // a new conversation is going to created when user sends first message.
            openConversationScreen(publicKey)
        else {
            TODO("create a snackBar or show toast message that says wrong public key")
        }
    }


    private fun openConversationScreen(conversationPublicKey: String) {
        data?.clearUnseen(conversationPublicKey)
        navController?.navigate(
            ChatScreenRoute(
                inboxPublicKey = inbox!!.publicKey,
                privateKey = privateKey!!,
                senderPublicKey = conversationPublicKey,
                aesKeyPin = pin!!,
                aesKeySalt = inbox!!.salt!!,
                conversationLabel =  inbox!!.label

            )
        )
    }


    // ---------------------------------------------------------------------------------------------

    fun startPolling() {

        val keyPair = E2EKeyGenerator.generateKeyPair()
        val aesKey = AesKeyGenerator.generateKey()

        Log.e(
            "encryptionKey json",
            E2EEncryptor.encryptAESKeyWithPublicKey(
                aesKey,
                E2EKeyGenerator.getPublicKeyFromString(inbox!!.publicKey)
            )
        )
        AesEncryptor.encryptMessage(
            """
            
            {
                "sender_public_key": "${keyPair.public.toText()}",
                "text": "Hello Hello Hello"
            }
            
            
            """.trimIndent(), aesKey
        ).apply {
            Log.e("encrypted message json", first)
            Log.e("iv json", second)
        }

        viewModelScope.launch {
            while (true) {
                try { // just making it unreadable I guess (why ?)
                    val newLastMessageId = incomingMessageHandler?.gotNewMessages(
                        apiService?.getMessage(
                            inbox!!.lastMessageId,
                            inbox!!.publicKey
                        ) ?: continue,
                        privateKey!!,
                        inbox!!.inboxId, AesKeyGenerator.generateKey(
                            pin!!,
                            inbox?.salt!!
                        )
                    ) ?: continue
                    inbox = inbox?.copy(lastMessageId = newLastMessageId)
                    loadConversations()

                } catch (ex: Exception) {
                    throw ex
                }
                delay(3000)  // Wait for 3 seconds before polling again

            }
        }
    }

}