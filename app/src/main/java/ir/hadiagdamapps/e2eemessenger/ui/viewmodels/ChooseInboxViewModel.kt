package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ir.hadiagdamapps.e2eemessenger.data.Clipboard
import ir.hadiagdamapps.e2eemessenger.data.TextFormat
import ir.hadiagdamapps.e2eemessenger.data.TextFormat.PIN_LENGTH
import ir.hadiagdamapps.e2eemessenger.data.database.InboxData
import ir.hadiagdamapps.e2eemessenger.data.models.InboxDialogModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.qr.QrCodeGenerator
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute


class ChooseInboxViewModel : ViewModel() {

    private val qrCodeGenerator = QrCodeGenerator()

    private var navController: NavController? = null
    private var data: InboxData? = null

    private val _inboxes = mutableStateListOf<InboxModel>()
    val inboxes: SnapshotStateList<InboxModel> = _inboxes


    var pin: String? by mutableStateOf(null)
        private set

    var pinDialogError: String? by mutableStateOf(null)
        private set

    var inboxDialog: InboxDialogModel? by mutableStateOf(null)
        private set


    fun init(navController: NavController, context: Context) {
        this.navController = navController
        data = InboxData(context)
        loadInboxes()
    }

    // ---------------------------------------------------------------------------------------

    fun inboxDialogDismiss() {
        if (inboxDialog != null)
            data?.updateLabel(
                inboxDialog!!.dialogPublicKey,
                inboxDialog!!.dialogLabel
            )
        inboxDialog = null
        loadInboxes()
    }

    fun inboxDialogLabelChange(newLabel: String) {
        inboxDialog = inboxDialog?.copy(dialogLabel = newLabel)
    }

    fun inboxDialogCopyPublicKey() {
        inboxDialog?.dialogPublicKey?.let { Clipboard.copy(it) }
    }

    fun showInboxDialog(inbox: InboxModel) {
        inboxDialog = InboxDialogModel(
            qrCode = qrCodeGenerator.generateCode(inbox.publicKey),
            dialogPublicKey = inbox.publicKey,
            dialogLabel = inbox.label
        )
    }

    // ---------------------------------------------------------------------------------------

    fun pinDialogDismiss() {
        pin = null
        pinDialogError = null
    }

    fun pinDialogSubmit() {
        if (TextFormat.isValidPin(pin)) data!!.newInbox(pin!!).apply {
            _inboxes.add(this)
            pinDialogDismiss()
            showInboxDialog(this)
            // TODO PROBLEM : Pin dialog opens itself after closing
        } else pinDialogError = "invalid pin"
    }

    fun pinChanged(newPin: String) {
        if (newPin.length < PIN_LENGTH + 1) {
            pin = newPin
            pinDialogError = null
        }
    }

    fun newInbox() {
        pin = ""
    }

    // ---------------------------------------------------------------------------------------

    fun inboxClick(inbox: InboxModel) {
        navController?.navigate(
            inbox
        )
    }

    fun deleteInbox() {
        if ((data ?: return).delete(inboxDialog?.dialogPublicKey ?: return))
            for (i in inboxes.toList())
                if (i.publicKey == inboxDialog?.dialogPublicKey) _inboxes.remove(i)
        inboxDialog = null
    }

    private fun loadInboxes() {
        _inboxes.clear()
        for (i in data?.getInboxes() ?: return) {
            _inboxes.add(i)
        }
    }


}

