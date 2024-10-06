package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography

@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    sendClick: () -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = sendClick) {
                Icon(
                    painter = painterResource(id = R.drawable.send_icon),
                    contentDescription = "send icon",
                    tint = ColorPalette.primary
                )
            }
        },
        placeholder = {
            Text(
                text = "Message", color = ColorPalette.primary, style = Typography.bodyMedium
            )
        },
        modifier = modifier
            .fillMaxHeight(.1f)
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ColorPalette.secondary,
            unfocusedContainerColor = ColorPalette.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = ColorPalette.primary
        ),
        maxLines = 1,
        singleLine = true
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatBoxPreview() {

    Screen(title = "Preview") {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            ChatBox(
                text = "",
                onValueChange =  {},
                sendClick = {}
            )
        }
    }

}

