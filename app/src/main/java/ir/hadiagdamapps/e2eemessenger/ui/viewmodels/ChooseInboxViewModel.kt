package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ir.hadiagdamapps.e2eemessenger.data.Clipboard
import ir.hadiagdamapps.e2eemessenger.data.database.InboxData
import ir.hadiagdamapps.e2eemessenger.data.models.InboxDialogModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.qr.QrCodeGenerator
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute


class ChooseInboxViewModel : ViewModel() {

    private val qrCodeGenerator = QrCodeGenerator()
    private var navController: NavController? = null
    private val data = InboxData()

    private val _inboxes = mutableStateListOf<InboxModel>()
    val inboxes: SnapshotStateList<InboxModel> = _inboxes

    val inboxDialog: MutableState<InboxDialogModel?> = mutableStateOf(null)


    fun init(navController: NavController) {
        this.navController = navController
    }

    fun dialogDismiss() {
        inboxDialog.value = null
    }

    fun dialogLabelChange(newLabel: String) {
        inboxDialog.value = inboxDialog.value?.copy(dialogLabel = newLabel)
    }


    fun dialogCopyPublicKey() {
        Clipboard.copy(inboxDialog.value!!.dialogPublicKey)
    }

    fun dialogDeleteInbox() {
        if (data.delete(inboxDialog.value?.dialogPublicKey!!))
            for (i in _inboxes)
                if (i.publicKey == inboxDialog.value?.dialogPublicKey)
                    _inboxes.remove(i)
    }


    fun inboxClick(inbox: InboxModel) {
        navController?.navigate(
            InboxScreenRoute(inbox.publicKey)
        )
    }


    fun showDialog(inbox: InboxModel) {
        inboxDialog.value =
            InboxDialogModel(
                qrCode = qrCodeGenerator.generateCode(inbox.publicKey),
                dialogPublicKey = inbox.publicKey,
                dialogLabel = inbox.label
            )
    }

    fun newInbox() {
        val newInbox = data.newInbox()
        _inboxes.add(newInbox)
    }

}

