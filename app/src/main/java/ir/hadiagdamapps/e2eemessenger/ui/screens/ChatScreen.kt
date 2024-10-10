package ir.hadiagdamapps.e2eemessenger.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.hadiagdamapps.e2eemessenger.ui.components.ChatBox
import ir.hadiagdamapps.e2eemessenger.ui.components.ChatDateText
import ir.hadiagdamapps.e2eemessenger.ui.components.ChatItem
import ir.hadiagdamapps.e2eemessenger.ui.components.PendingChatItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.viewmodels.ChatScreenViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatScreenViewModel) {

    LaunchedEffect(key1 = viewModel.isPolling) {
        if (viewModel.isPolling) viewModel.startPolling()
    }

    Screen(
        title = viewModel.conversationLabel,
    ) {

        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            ChatBox(
                text = viewModel.chatBoxContent,
                onValueChange = viewModel::chatBoxTextChange,
                sendClick = viewModel::chatBoxSubmit
            )
        }) { padding ->
            var t = 0L
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                items(viewModel.chats) { // items should be sorted

                    if (!isSameDay(t, it.time)) {
                        ChatDateText(timestamp = it.time)
                        t = it.time
                    }
                    ChatItem(text = it.text, timeStamp = it.time, sent = it.sent)
                }
            }
            LazyColumn {
                items(viewModel.pendingMessages) {
                    PendingChatItem(text = it.second) {
                        viewModel.cancelSending(it.first)
                    }
                }
            }
        }
    }
}


private fun isSameDay(t1: Long, t2: Long): Boolean {
    val dt1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(t1), ZoneId.systemDefault())
    val dt2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(t2), ZoneId.systemDefault())

    return dt1.year == dt2.year && dt1.month == dt2.month && dt1.dayOfMonth == dt2.dayOfMonth
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatScreenPreview() {
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
