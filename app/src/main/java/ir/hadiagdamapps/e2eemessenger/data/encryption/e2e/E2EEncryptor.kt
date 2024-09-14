package ir.hadiagdamapps.e2eemessenger.data.encryption.e2e

import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object E2EEncryptor {

    fun encryptAESKeyWithPublicKey(aesKey: SecretKey, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance("ECIES") // Elliptic Curve Integrated Encryption Scheme (ECIES)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedAESKey = cipher.doFinal(aesKey.encoded)

        return Base64.getEncoder().encodeToString(encryptedAESKey)
    }

    fun decryptAESKeyWithPrivateKey(encryptedAESKey: String, privateKey: PrivateKey): SecretKey {
        val cipher = Cipher.getInstance("ECIES")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decodedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey))

        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }
}