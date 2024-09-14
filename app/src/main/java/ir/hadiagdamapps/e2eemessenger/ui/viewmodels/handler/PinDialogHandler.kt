package ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler

import androidx.lifecycle.MutableLiveData

class PinDialogHandler {

    val pin: MutableLiveData<String?> = MutableLiveData(null)
    val pinDialogError: MutableLiveData<String?> = MutableLiveData(null)


    fun pinChanged(newPin: String) {
        if (newPin.length < 5)
            pin.value = newPin
    }


    fun submit() {
        TODO()
    }

    fun dismiss() {
        pin.value = null
        pinDialogError.value = null
    }

}