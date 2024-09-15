package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ir.hadiagdamapps.e2eemessenger.data.database.InboxData
import ir.hadiagdamapps.e2eemessenger.data.models.InboxDialogModel
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler.InboxDetailsDialogHandler
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler.PinDialogHandler


class ChooseInboxViewModel : ViewModel() {

    private val inboxDialogHandler = InboxDetailsDialogHandler()
    private val pinHandler = PinDialogHandler {
       val inbox = data!!.newInbox(it)
        _inboxes.add(inbox)
    }
    private var navController: NavController? = null
    private var data: InboxData? = null

    private val _inboxes = mutableStateListOf<InboxModel>()
    val inboxes: SnapshotStateList<InboxModel> = _inboxes

    val inboxDialog: LiveData<InboxDialogModel?>
        get() = inboxDialogHandler.inboxDialog

    val pin: LiveData<String?>
        get() = pinHandler.pin

    val pinDialogError: LiveData<String?>
        get() = pinHandler.pinDialogError


    fun init(navController: NavController, context: Context) {
        this.navController = navController
        data = InboxData(context)
    }

    // ---------------------------------------------------------------------------------------

    fun inboxDialogDismiss() = inboxDialogHandler::dismiss

    fun inboxDialogLabelChange(newLabel: String) = inboxDialogHandler::labelChanged

    fun inboxDialogCopyPublicKey() = inboxDialogHandler::copyPublicKey

    fun showInboxDialog(inbox: InboxModel) = inboxDialogHandler::showDialog

    // ---------------------------------------------------------------------------------------

    fun pinDialogDismiss() = PinDialogHandler::dismiss

    fun pinDialogSubmit() = pinHandler::submit

    fun pinChanged(newPin: String) = pinHandler::pinChanged

    // ---------------------------------------------------------------------------------------

    fun newInbox() {
        pinHandler.pin.value = ""
    }

    fun inboxClick(inbox: InboxModel) {
        navController?.navigate(
            InboxScreenRoute(inbox.publicKey)
        )
    }

    fun dialogDeleteInbox() {
        if (data!!.delete(inboxDialog.value?.dialogPublicKey!!))
            for (i in _inboxes)
                if (i.publicKey == inboxDialog.value?.dialogPublicKey)
                    _inboxes.remove(i)
        inboxDialogHandler.delete()
    }


}

