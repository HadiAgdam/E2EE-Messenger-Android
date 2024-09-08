package ir.hadiagdamapps.e2eemessenger.data.network

import com.google.gson.GsonBuilder
import ir.hadiagdamapps.e2eemessenger.data.models.PublicKey
import ir.hadiagdamapps.e2eemessenger.data.network.adapters.PublicKeyAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val gson = GsonBuilder()
        .registerTypeAdapter(PublicKey::class.java, PublicKeyAdapter())
        // TODO add other adapters too
        .create()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}