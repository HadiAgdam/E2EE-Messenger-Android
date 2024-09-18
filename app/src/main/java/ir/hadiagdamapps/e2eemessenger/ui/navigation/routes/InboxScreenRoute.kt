package ir.hadiagdamapps.e2eemessenger.ui.navigation.routes

import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import kotlinx.serialization.Serializable

@Serializable
data class InboxScreenRoute(
    val inbox: InboxModel
)
