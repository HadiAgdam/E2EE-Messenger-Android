package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography

@Composable
fun ConfirmDeleteDialog(
    onOkClick: () -> Unit, onCancelClick: () -> Unit, body: String = "Are you sure want to delete ?"
) {


    AlertDialog(shape = RectangleShape, onDismissRequest = onCancelClick, confirmButton = {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancelClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "No", color = Color.Cyan,
                )
            }

            Button(
                onClick = onOkClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Yes", color = Color.Cyan)
            }
        }

    },

        text = {
            Column {
                Text(text = body, style = Typography.bodyLarge, color = Color.White)
            }
        })

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ConfirmDeleteDialogPreview() {
    E2EEMessengerTheme {
        Screen(title = "Preview") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ConfirmDeleteDialog(onOkClick = { /*TODO*/ }, onCancelClick = { /*TODO*/ })
            }
        }
    }
}
