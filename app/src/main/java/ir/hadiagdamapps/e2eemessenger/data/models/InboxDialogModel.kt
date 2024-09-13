package ir.hadiagdamapps.e2eemessenger.data.models

import android.graphics.Bitmap

data class InboxDialogModel(
    val qrCode: Bitmap,
    val dialogPublicKey: String,
    val dialogLabel: String
)