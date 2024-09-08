package ir.hadiagdamapps.e2eemessenger

import ir.hadiagdamapps.e2eemessenger.data.models.messages.OutgoingMessage
import ir.hadiagdamapps.e2eemessenger.data.network.ApiService
import ir.hadiagdamapps.e2eemessenger.data.network.RetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ApiServiceTest {

    private lateinit var apiService: ApiService

    @Before
    fun setUp() {

        apiService = RetrofitInstance.api
    }



    @Test
    fun testNewMessage() = runBlocking {
        val message = OutgoingMessage(
            "public key",
            "encryption key",
            "message"
        )


        val response = apiService.newMessage(message)


        Assert.assertTrue(response.isSuccessful)
    }

    @Test
    fun testGetMessage() = runBlocking {

        val response = apiService.getMessage(0, "")

        println()
        println(response.body())
        println()




    }

}