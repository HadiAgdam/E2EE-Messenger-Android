package ir.hadiagdamapps.e2eemessenger.data.database

import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel

class InboxData {


    // returns true of successful
    fun delete(publicKey: String): Boolean {
        TODO()
    }


    fun delete(model: InboxModel) = delete(model.publicKey)

    // create a new inbox and return it
    fun newInbox(pin: String): InboxModel {
        TODO()
    }

}