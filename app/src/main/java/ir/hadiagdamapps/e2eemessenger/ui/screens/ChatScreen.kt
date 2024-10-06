package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.hadiagdamapps.e2eemessenger.ui.components.ChatBox
import ir.hadiagdamapps.e2eemessenger.ui.components.ChatItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChatScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatScreenViewModel) {

    Screen(
        title = viewModel.conversationLabel,
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(viewModel.chats) {
                    ChatItem(text = it.text, timeStamp = it.time, sent = it.sent)
                }
            }
            ChatBox(text = viewModel.chatBoxContent, onValueChange = viewModel::chatBoxTextChange) {

            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatScreenPreview() {
    Screen(
        title = "label",
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

            }
            ChatBox(text = "", onValueChange = {}) {

            }

        }

    }
}
