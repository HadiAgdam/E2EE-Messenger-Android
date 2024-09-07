package ir.hadiagdamapps.e2eemessenger.data.network

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ir.hadiagdamapps.e2eemessenger.data.models.EncryptedMessage
import ir.hadiagdamapps.e2eemessenger.data.models.MessageEncryptionKey
import ir.hadiagdamapps.e2eemessenger.data.models.MessageReceiver

var queue: RequestQueue? = null


object Network {
    fun initQueue(context: Context) {
        queue = Volley.newRequestQueue(context)
    }

    private fun newRequest(
        endPoint: ServerEndPoint,
        onSuccess: (response: String) -> Unit,
        onError: (VolleyError) -> Unit,
        params: HashMap<String, String>? = null
    ) {
        if (queue == null) throw Exception("null queue")

        val request = object : StringRequest(endPoint.method.volleyMethod,
            endPoint.url,
            onSuccess,
            { error: VolleyError ->
                Log.e("server request volley error", error.toString())
                onError(error)
            }

        ) {
            override fun getParams(): MutableMap<String, String>? {
                return params
            }
        }

        queue!!.add(request)
    }


    fun sendMessage(
        receiver: MessageReceiver,
        encryptionKey: MessageEncryptionKey,
        message: EncryptedMessage,
        onSuccess: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        newRequest(endPoint = ServerEndPoint.NEW_MESSAGE,
            onSuccess = { onSuccess() },
            onError = onError,
            params = HashMap<String, String>().apply {
                put(Arguments.RECEIVER, receiver.toString())
                put(Arguments.KEY, encryptionKey.toString())
                put(Arguments.MESSAGE, message.toString())
            })
    }
}