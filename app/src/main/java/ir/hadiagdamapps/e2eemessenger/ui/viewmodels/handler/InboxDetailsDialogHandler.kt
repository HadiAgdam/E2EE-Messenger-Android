package ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler

import androidx.lifecycle.MutableLiveData
import ir.hadiagdamapps.e2eemessenger.data.Clipboard
import ir.hadiagdamapps.e2eemessenger.data.models.InboxDialogModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.qr.QrCodeGenerator

class InboxDetailsDialogHandler {

    private val qrCodeGenerator = QrCodeGenerator()

    val inboxDialog: MutableLiveData<InboxDialogModel?> = MutableLiveData(null)


    fun dismiss() {
        inboxDialog.value = null
    }

    fun labelChanged(newLabel: String) {
        inboxDialog.value = inboxDialog.value?.copy(dialogLabel = newLabel)
    }

    fun copyPublicKey() {
        Clipboard.copy(inboxDialog.value!!.dialogPublicKey)

    }

    fun delete() {
        inboxDialog.value = null
    }

    fun showDialog(inbox: InboxModel) {
        inboxDialog.value =
            InboxDialogModel(
                qrCode = qrCodeGenerator.generateCode(inbox.publicKey),
                dialogPublicKey = inbox.publicKey,
                dialogLabel = inbox.label
            )
    }

}