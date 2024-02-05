package online.nsandroid.smartbmicalculator.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40


@Composable
fun ScaleCenterPointer() {
    val primaryColor = LightGreen40

    Column {
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .offset(y = (-136).dp)
                .width(3.dp),
        ) {
            drawCircle(
                color = primaryColor,
                radius = 16.dp.toPx(),
            )
        }
    }
}

@Composable
fun ScaleLineComponent(
    index: Int
) {
    val isDivisibleBy10 = index % 10 == 0
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .background(surfaceColor)
    ) {
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .height(64.dp)
                .width(3.dp),
        ) {
            drawLine(
                color = onSurfaceColor,
                start = Offset(0f, 0f),
                end = Offset(0f, if (isDivisibleBy10) size.height else size.height * 0.2f),
                strokeWidth = if (isDivisibleBy10) size.width else size.width * 0.3f
            )
        }

        Text(
            text = if (isDivisibleBy10) "${index / 10}" else "",
            textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.Monospace),
            color = onSurfaceColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MeasuringScaleComponent() {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(top = 20.dp)
            .fillMaxWidth(),
        content = {
            for (i in 0..1020) {
                ScaleLineComponent(index = i)
            }
        }
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        ScaleCenterPointer()
    }
}