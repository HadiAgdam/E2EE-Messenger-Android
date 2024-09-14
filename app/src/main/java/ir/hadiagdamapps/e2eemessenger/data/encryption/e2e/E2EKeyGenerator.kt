package ir.hadiagdamapps.e2eemessenger.data.encryption.e2e

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.spec.ECGenParameterSpec
import java.util.Base64

object E2EKeyGenerator {

    fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("EC")
        val ecSpec = ECGenParameterSpec("secp256r1")
        keyGen.initialize(ecSpec)
        return keyGen.genKeyPair()
    }


    fun ByteArray.toText(): String {
        return Base64.getEncoder().encodeToString(this)
    }


}