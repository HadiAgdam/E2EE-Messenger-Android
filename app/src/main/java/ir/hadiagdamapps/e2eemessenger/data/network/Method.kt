package ir.hadiagdamapps.e2eemessenger.data.network

import com.android.volley.Request

enum class Method(val volleyMethod: Int) {
    GET(Request.Method.GET),
    POST(Request.Method.POST)
}