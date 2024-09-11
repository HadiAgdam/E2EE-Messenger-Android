package ir.hadiagdamapps.e2eemessenger.data.models

import android.graphics.Bitmap

data class InboxDialogModel(
    val qrCode: Bitmap,
    val publicKey: String,
    val label: String
)