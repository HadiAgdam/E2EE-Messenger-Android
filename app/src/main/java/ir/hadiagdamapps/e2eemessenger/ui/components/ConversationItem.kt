package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography

@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    label: String,
    detailsClick: () -> Unit,
    lastMessageText: String,
    timeText: String,
    unseenMessagesCount: Int
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorPalette.background)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = Typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(.05f))

            if (unseenMessagesCount > 0)
                UnseenMessageCountBadge(count = unseenMessagesCount)
            Spacer(modifier = Modifier.weight(.02f))
            IconButton(onClick = detailsClick, modifier = Modifier.fillMaxWidth(.1f)) {
                Icon(
                    painter = painterResource(id = R.drawable.more_icon),
                    contentDescription = "more",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = lastMessageText,
                maxLines = 1,
                color = ColorPalette.primary,
                style = Typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(text = timeText, color = ColorPalette.primary, style = Typography.bodyLarge)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ConversationItemPreview() {
    Screen(title = "Preview") {
        Column(modifier = Modifier.fillMaxSize()) {
            ConversationItem(
                label = "labelasdsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd",
                detailsClick = { /*TODO*/ },
                lastMessageText = "last message",
                timeText = "2024-03-08 12:22",
                unseenMessagesCount = 10
            )
        }
    }
}
