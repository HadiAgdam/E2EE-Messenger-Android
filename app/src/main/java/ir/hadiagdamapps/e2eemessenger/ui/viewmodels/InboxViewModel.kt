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
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.models.ChatPreviewModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute


class InboxViewModel : ViewModel() {

    private var inbox: InboxModel? = null
    private var navController: NavHostController? = null

    private val _chats = mutableStateListOf<ChatPreviewModel>()
    val chats: SnapshotStateList<ChatPreviewModel> = _chats

    var pin: String? by mutableStateOf("")
        private set

    var pinDialogError: String? by mutableStateOf(null)
        private set

    private var privateKey: String? = null

    private var displayWrongPinMessage: (() -> Unit)? = null


    // TODO user should enter pin to generate private key first

    // Dear future me, please forgive me. I know this is wrong but I don't know any better way.
    fun init(navController: NavHostController, inbox: InboxScreenRoute, context: Context) {
        this.inbox = inbox
        this.navController = navController
        displayWrongPinMessage = {
            // WTF is going on here ?
            Toast.makeText(context, "wrong pin", Toast.LENGTH_SHORT).show()
        }
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
            }
        }
     else pinDialogError = "invalid pin format"



    fun loadMessages() = if (privateKey != null) {

        // TODO generate AES encryption key for messages using private key and message encryptionKey
        // TODO get the message from db and decrypt them using AES key

        } else {
            navController?.popBackStack()
            displayWrongPinMessage?.invoke()
        }


    fun newChat() {

    }

}