package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme


val padding = 16.dp

@Composable
fun InboxDetailsDialog() {

    val publicKey = "PUBLIC_KEY PUBLIC_KEY PUBLIC_KEY PUBLIC_KEY PUBLIC_KEY PUBLIC_KEY PUBLIC_KEY"
    var label = "Label"

    val dismiss: () -> Unit = {

    }
    val copyPublicKEy: () -> Unit = {

    }
    
    val delete: () -> Unit = {

    }

    E2EEMessengerTheme {

        AlertDialog(shape = RectangleShape, text = {

            Column(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "qr code container",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(padding))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = publicKey,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                    IconButton(onClick = copyPublicKEy, modifier = Modifier.size(36.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.copy_icon),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }


            }
        }, onDismissRequest = dismiss, confirmButton = {

            TextField(value = publicKey,
                maxLines = 1,
                colors = TextFieldDefaults.colors(),
                onValueChange = {
                    label = it
                })



            Button(
                onClick = delete,
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPalette.dangerRed)
            ) {

                Text(text = "DELETE")

            }
        })

    }

}


@Preview
@Composable
fun InboxDetailsDialogPreview() {
    Screen(title = "Preview") {
        InboxDetailsDialog()
    }
}
