package ir.hadiagdamapps.e2eemessenger.data.network

import ir.hadiagdamapps.e2eemessenger.data.models.messages.IncomingMessage
import ir.hadiagdamapps.e2eemessenger.data.models.messages.OutgoingMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("new_message")
    suspend fun newMessage(@Body message: OutgoingMessage): Response<Unit>

    @GET("get_message")
    suspend fun getMessage(
        @Query("messageId") messageId: Int,
        @Query("publicKey") publicKey: String
    ): Response<List<IncomingMessage>>

}
