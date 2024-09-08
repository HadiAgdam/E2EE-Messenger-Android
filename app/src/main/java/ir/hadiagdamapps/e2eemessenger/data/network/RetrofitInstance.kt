package ir.hadiagdamapps.e2eemessenger.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://hadiagdam0.pythonanywhere.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}