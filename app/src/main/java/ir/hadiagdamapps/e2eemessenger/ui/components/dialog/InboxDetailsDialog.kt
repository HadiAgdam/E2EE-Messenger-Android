package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.data.models.InboxDialogModel
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette


val padding = 16.dp

@Composable
fun InboxDetailsDialog(
    model: InboxDialogModel,
    dismiss: () -> Unit,
    copyPublicKey: () -> Unit,
    deleteInbox: () -> Unit,
    labelValueChange: (String) -> Unit
) {


    AlertDialog(shape = RectangleShape, text = {

        Column(modifier = Modifier.fillMaxWidth()) {

            Image(
                bitmap = model.qrCode.asImageBitmap(),
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
                    text = model.dialogPublicKey,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    maxLines = 2
                )
                IconButton(onClick = copyPublicKey, modifier = Modifier.size(36.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.copy_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }


        }
    }, onDismissRequest = dismiss, confirmButton = {

        TextField(
            value = model.dialogLabel,
            maxLines = 1,
            colors = TextFieldDefaults.colors(),
            onValueChange = labelValueChange
        )



        Button(
            onClick = deleteInbox,
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = ColorPalette.dangerRed)
        ) {

            Text(text = "DELETE")

        }
    })

}


