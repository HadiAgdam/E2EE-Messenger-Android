package ir.hadiagdamapps.e2eemessenger.data.network.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import ir.hadiagdamapps.e2eemessenger.data.models.PublicKey

class PublicKeyAdapter: TypeAdapter<PublicKey>() {
    override fun write(writer: JsonWriter?, value: PublicKey?) {
        TODO("Not yet implemented")
    }

    override fun read(reader: JsonReader?): PublicKey {
        TODO("Not yet implemented")
    }
}