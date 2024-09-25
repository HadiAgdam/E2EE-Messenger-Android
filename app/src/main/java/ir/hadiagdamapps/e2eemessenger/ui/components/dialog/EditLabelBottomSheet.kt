package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import ir.hadiagdamapps.e2eemessenger.ui.components.widgets.TextButton
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun EditLabelBottomSheet(
    text: String, onOkClick: () -> Unit, onCancelClick: () -> Unit, onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Edit Label", color = Color.White, style = Typography.titleMedium)
        }


        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "enter label",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            TextButton(text = "cancel", onClick = onCancelClick)

            TextButton(text = "ok", onClick = onOkClick)

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditLabelBottomSheetDialogPreview() {

    E2EEMessengerTheme {


        val state = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                true, initialValue = SheetValue.Expanded
            )
        )

        var text by remember {
            mutableStateOf("")
        }

        Screen(title = "Preview", scaffoldState = state, sheetContent = {
            EditLabelBottomSheet(text = text,
                onOkClick = { /*TODO*/ },
                onCancelClick = { /*TODO*/ }) {
                text = it
            }
        }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "content", color = Color.White)
            }
        }
    }
}
