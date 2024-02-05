package online.nsandroid.smartbmicalculator.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.nsandroid.smartbmicalculator.ui.theme.DarkGrey
import online.nsandroid.smartbmicalculator.ui.theme.Grey
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40
import online.nsandroid.smartbmicalculator.ui.theme.SmartBMICalculatorTheme


@Composable
fun ScaleCenterPointer() {
    val primaryColor = LightGreen40

    Column {
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .offset(y = (-112).dp)
                .width(3.dp),
        ) {
            drawCircle(
                color = primaryColor,
                radius = 12.dp.toPx(),
            )
        }
    }
}

@Composable
fun ScaleLineComponent(
    index: Int
) {
    val isDivisibleBy10 = index % 2 == 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Canvas(
            modifier = Modifier
                .padding(12.dp)
                .height(20.dp)
                .fillMaxWidth()
                .width(4.dp),
        ) {
            drawLine(
                color = DarkGrey,
                start = Offset(0f, 0f),
                end = Offset(0f, if (isDivisibleBy10) size.height else size.height * 0.6f),
                strokeWidth = if (isDivisibleBy10) size.width * 0.8F else size.width * 0.6f
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            text = if (isDivisibleBy10) "${index / 1}" else " ",
            textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.Monospace),
            color = DarkGrey,
        )
    }
}

@Composable
fun MeasuringScaleComponent() {
    val scrollState = rememberScrollState()
    Spacer(modifier = Modifier.height(16.dp))
    Divider(color = Grey, thickness = 6.dp)
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(top = 20.dp)
            .fillMaxWidth(),
        content = {
            for (i in -6..500) {
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

@Preview
@Composable
private fun PreviewMeasuringScaleComponent() {
    SmartBMICalculatorTheme {
        MeasuringScaleComponent()
    }
}