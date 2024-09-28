package ir.hadiagdamapps.e2eemessenger.data.encryption.e2e

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object E2EEncryptor {

    fun encryptAESKeyWithPublicKey(aesKey: SecretKey, publicKey: String): String {
        val cipher = Cipher.getInstance("ECIES")
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(publicKey))
        val encryptedAESKey = cipher.doFinal(aesKey.encoded)

        return Base64.getEncoder().encodeToString(encryptedAESKey)
    }

    fun decryptAESKeyWithPrivateKey(encryptedAESKey: String, privateKey: String): SecretKey {
        val cipher = Cipher.getInstance("ECIES")
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString(privateKey))
        val decodedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey))

        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }


    private fun getPublicKeyFromString(keyString: String): PublicKey {
        val keyBytes = Base64.getDecoder().decode(keyString)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("EC")

        return keyFactory.generatePublic(keySpec)
    }


    private fun getPrivateKeyFromString(keyString: String): PrivateKey {
        val keyBytes = Base64.getDecoder().decode(keyString)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("EC")

        return keyFactory.generatePrivate(keySpec)
    }
}