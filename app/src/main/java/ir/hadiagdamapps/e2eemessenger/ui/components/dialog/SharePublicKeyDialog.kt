package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme

@Composable
fun SharePublicKeyDialog(
    qrCode: Bitmap, publicKey: String, copyClick: () -> Unit, dismiss: () -> Unit
) {

    AlertDialog(shape = RectangleShape, text = {

        Column(modifier = Modifier.fillMaxWidth()) {

            Image(
                bitmap = qrCode.asImageBitmap(),
                contentDescription = "qr code container",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

        }
    }, onDismissRequest = dismiss, confirmButton = {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            IconButton(onClick = copyClick, modifier = Modifier.size(36.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.copy_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    })

}
