package ir.hadiagdamapps.e2eemessenger.data.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter

data class MenuItem(
    val text: String,
    @DrawableRes val icon: Int
)