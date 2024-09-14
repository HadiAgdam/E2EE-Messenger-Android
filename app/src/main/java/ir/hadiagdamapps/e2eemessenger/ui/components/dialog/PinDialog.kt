package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography


@Composable
fun PinDialog(
    pin: String,
    onPinChanged: (String) -> Unit,
    error: String?,
    dismiss: () -> Unit,
    okClick: () -> Unit
) {
    AlertDialog(shape = RectangleShape, onDismissRequest = dismiss, text = {
        Column {


            Text(
                text = "Enter Pin",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = pin,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                onValueChange = onPinChanged,
                placeholder = {
                    Text(
                        text = "PIN",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                maxLines = 1,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (error != null)
                Text(
                    text = error,
                    color = Color.Red,
                    style = Typography.labelLarge
                )
        }

    }, confirmButton = {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = dismiss,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "CANCEL", color = Color.Cyan,
                )
            }

            Button(
                onClick = okClick,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "OK", color = Color.Cyan)
            }
        }
    })


}