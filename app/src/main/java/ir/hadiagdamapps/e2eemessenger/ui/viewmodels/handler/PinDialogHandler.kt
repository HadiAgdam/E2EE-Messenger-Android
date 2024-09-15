package ir.hadiagdamapps.e2eemessenger.ui.viewmodels.handler

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData

class PinDialogHandler(
    private val submitPin: (pin: String) -> Unit
) {

    private val pinLength = 6
    val pin: MutableLiveData<String?> = MutableLiveData(null)
    val pinDialogError: MutableLiveData<String?> = MutableLiveData(null)


    fun pinChanged(newPin: String) {
        if (newPin.length < pinLength + 1)
            pin.value = newPin
        pinDialogError.value = null
    }


    fun submit() { // WTF am I doing ?
        if (pin.value != null && pin.value!!.isDigitsOnly() && pin.value!!.length == pinLength)
            submitPin(pin.value!!)
        else pinDialogError.value = "invalid pin"

    }

    fun dismiss() {
        pin.value = null
        pinDialogError.value = null
    }


}

