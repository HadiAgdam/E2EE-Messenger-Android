package ir.hadiagdamapps.e2eemessenger.data.network.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import ir.hadiagdamapps.e2eemessenger.data.models.EncryptionKey

class EncryptionKeyAdapter: TypeAdapter<EncryptionKey>() {

    override fun write(out: JsonWriter?, value: EncryptionKey?) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader?): EncryptionKey {
        TODO("Not yet implemented")
    }
}