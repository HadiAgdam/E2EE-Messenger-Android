package ir.hadiagdamapps.e2eemessenger.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.R
import ir.hadiagdamapps.e2eemessenger.data.models.MenuItem
import ir.hadiagdamapps.e2eemessenger.ui.components.Screen
import kotlinx.coroutines.launch

@Composable
fun BottomSheetMenu(items: List<MenuItem>, onItemClick: (MenuItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        for (item in items)
        TextButton(onClick = { onItemClick(item) }, modifier = Modifier.fillMaxWidth(), shape = RectangleShape) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = item.text, color = Color.White)
                Icon(painter = painterResource(id = item.icon), contentDescription = null, tint = Color.White)
            }
        }
    }
}
