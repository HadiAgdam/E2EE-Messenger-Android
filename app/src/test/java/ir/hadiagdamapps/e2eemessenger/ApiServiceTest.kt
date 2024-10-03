package ir.hadiagdamapps.e2eemessenger

import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText
import ir.hadiagdamapps.e2eemessenger.data.models.messages.IncomingMessage
import ir.hadiagdamapps.e2eemessenger.data.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Security
import kotlin.math.log

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetMessage(): Unit = runBlocking {
        Security.addProvider(BouncyCastleProvider())


        val keyPair = E2EKeyGenerator.generateKeyPair()
        val aesKey = AesKeyGenerator.generateKey()

        val encryptedAesKey = E2EEncryptor.encryptAESKeyWithPublicKey(aesKey, keyPair.public.encoded.toText())

        val text = "Hello, World !"
        val encryptedMessage = AesEncryptor.encryptMessage(text, aesKey)

//        val mockResponse = Response.success(IncomingMessage(
//            messageId = 0,
//            encryptionKey =  encryptedAesKey,
//            encryptedMessage = encryptedMessage.first,
//            time = .0,
//            iv = encryptedMessage.second
//        ))
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
            [
                {
                 "messageId": 0,
                 "encryptionKey": "$encryptedAesKey",
                 "encryptedMessage": "${encryptedMessage.first}",
                 "time": 0.0,
                 "iv": "${encryptedMessage.second}"
                }
            ]
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val response = apiService.getMessage(0, keyPair.public.encoded.toText())

        println(response.body()!!.size.toString())
    }


}