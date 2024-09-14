package ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData

class PinDialogHandler(
    private val submitPin: (pin: String) -> Unit
) {

    val pin: MutableLiveData<String?> = MutableLiveData(null)
    val pinDialogError: MutableLiveData<String?> = MutableLiveData(null)


    fun pinChanged(newPin: String) {
        if (newPin.length < 5)
            pin.value = newPin
        pinDialogError.value = null
    }


    fun submit() { // WTF am I doing ?
        if (pin.value != null && pin.value!!.isDigitsOnly() && pin.value!!.length == 4) submitPin(pin.value!!)
        else pinDialogError.value = "invalid pin"

    }

    fun dismiss() {
        pin.value = null
        pinDialogError.value = null
    }


}

