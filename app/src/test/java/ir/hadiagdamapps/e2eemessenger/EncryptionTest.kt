package ir.hadiagdamapps.e2eemessenger

import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import java.security.Security

class EncryptionTest {

    @Test
    fun test1() {
        Security.addProvider(BouncyCastleProvider())


        val pair = E2EKeyGenerator.generateKeyPair()

        val publicKey = pair.public.encoded.toText()
        val privateKey = pair.private.encoded.toText()


        println("public key : $publicKey")
        println("private key : $privateKey")


        val aesKey = AesKeyGenerator.generateKey()
        val encryptedAesKey = E2EEncryptor.encryptAESKeyWithPublicKey(aesKey, pair.public)

        println("aes key : ${aesKey.encoded.toText()}")
        println("encrypted Aes key : $encryptedAesKey")


        val message = "Hello, World !"
        val (encryptedMessage, iv) = AesEncryptor.encryptMessage(message, aesKey)

        println("encrypted message : $encryptedMessage")
        println("iv : $iv")

        val decryptedAesKey = E2EEncryptor.decryptAESKeyWithPrivateKey(encryptedAesKey, pair.private)

        val decryptedMessage = AesEncryptor.decryptMessage(encryptedMessage, decryptedAesKey, iv)


        println("decrypted message : $decryptedMessage")



    }

}