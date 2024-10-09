package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

val boxPadding = 30.dp
const val oneLineLength = 20

@Composable
fun ChatItem(
    modifier: Modifier = Modifier, text: String, timeStamp: Long, sent: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal =  12.dp, vertical = 4.dp)
            .padding(start = if (sent) boxPadding else 0.dp, end = if (sent) 0.dp else boxPadding),
        contentAlignment = if (sent) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(if (sent) ColorPalette.primary else ColorPalette.secondary)
                .padding(12.dp)
        ) {
            val show = text.length < oneLineLength
            val dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
            val timeText = "${dt.hour}:${dt.minute}"
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                if (sent) {
                    if (show) Text(
                        text = timeText,
                        color = Color.LightGray,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    ChatTextContent(text = text)
                } else {
                    ChatTextContent(text = text)
                    Spacer(modifier = Modifier.width(12.dp))
                    if (show) Text(
                        text = timeText,
                        color = Color.LightGray,
                    )
                }
            }
            if (!show) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.align(if(sent) Alignment.Start else Alignment.End)) {
                    Text(text = "19:05", color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun ChatTextContent(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, color = Color.White, style = Typography.bodyMedium, modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun ChatItemPreview() {
    Screen(title = "Preview") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorPalette.background)
        ) {

            ChatItem(
                text = "0000000000000000000000000",
                timeStamp = System.currentTimeMillis(),
                sent = true
            )
        }
    }
}