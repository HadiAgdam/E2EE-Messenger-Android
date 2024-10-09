package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatDateText(timestamp: Long) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), contentAlignment = Alignment.Center) {
        Text(
            text = dateTime.format(formatter),
            color = ColorPalette.primary,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }

}


@Preview
@Composable
fun ChatDateTextPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.background)
    ) {
        ChatDateText(timestamp = System.currentTimeMillis())
    }
}

