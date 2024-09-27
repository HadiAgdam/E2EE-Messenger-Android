package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.theme.E2EEMessengerTheme
import ir.hadiagdamapps.e2eemessenger.ui.theme.Typography

@Composable
fun UnseenMessageCountBadge(
    modifier: Modifier = Modifier,
    count: Int
) {

    Box(modifier = modifier
        .size(24.dp)
        .clip(CircleShape.copy(CornerSize(100)))
        .background(Color.White),
        contentAlignment = Alignment.Center
        ) {
        Text(text = count.toString(), color = Color.Black, style = Typography.labelMedium)
    }


}
