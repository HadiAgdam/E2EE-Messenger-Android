package ir.hadiagdamapps.e2eemessenger.data.network

const val SERVER = ""

enum class ServerEndPoint(val url: String, val method: Method) {
    NEW_MESSAGE("$SERVER/api/new_message", Method.POST),
    GET_MESSAGE("$SERVER/api/get_message", Method.GET)
}