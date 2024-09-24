package ir.hadiagdamapps.e2eemessenger.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.e2eemessenger.ui.theme.ColorPalette
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    title: String,
    @DrawableRes icon: Int = -1,
    iconClick: () -> Unit = {},
    iconTint: Color = Color.White,
    fabClick: (() -> Unit)? = null,
    sheetContent: (@Composable ColumnScope.() -> Unit) = {},
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    content: @Composable () -> Unit,
) {


    BottomSheetScaffold(topBar = {
        TopBar(
            title = title, icon = icon, iconClick = iconClick, iconTint = iconTint
        )
    },
        sheetShape = RectangleShape,
        sheetContent = sheetContent,
        sheetDragHandle = null,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        content = { padding ->

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding), color = ColorPalette.background
            ) {

                Box(modifier = Modifier.fillMaxSize()) {

                    content()


                    if (fabClick != null) FloatingActionButton(
                        onClick = fabClick,
                        containerColor = Color.Black,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(72.dp)
                            .align(Alignment.BottomEnd)
                            .padding(0.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = "FAB Icon",
                            tint = Color.White
                        )
                    }

                }

            }


        })

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ScreenPreview() {
    Screen(title = "Preview", content = {})
}
