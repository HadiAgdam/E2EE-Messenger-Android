package ir.hadiagdamapps.e2eemessenger.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.ui.navigation.routes.InboxScreenRoute



class InboxViewModel : ViewModel() {

    private var inbox: InboxModel? = null

    fun init(navController: NavHostController, arguments: InboxScreenRoute) {
        inbox = arguments.inbox
    }

}