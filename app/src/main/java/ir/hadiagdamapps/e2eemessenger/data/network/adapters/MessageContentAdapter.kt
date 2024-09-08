package ir.hadiagdamapps.e2eemessenger.data.network.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import ir.hadiagdamapps.e2eemessenger.data.models.messages.MessageContent

class MessageContentAdapter: TypeAdapter<MessageContent>() {
    override fun write(out: JsonWriter?, value: MessageContent?) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader?): MessageContent {
        TODO("Not yet implemented")
    }

}