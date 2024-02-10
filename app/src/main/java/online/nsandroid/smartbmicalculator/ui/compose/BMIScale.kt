package online.nsandroid.smartbmicalculator.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.nsandroid.smartbmicalculator.ui.theme.DarkGrey
import online.nsandroid.smartbmicalculator.ui.theme.Grey
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40
import online.nsandroid.smartbmicalculator.ui.theme.SmartBMICalculatorTheme
import online.nsandroid.smartbmicalculator.ui.util.findClosest

@Composable
fun MeasuringScaleComponent(
    matchingPosition: (Int) -> Unit,
    list: MutableList<Int> = emptyList<Int>().toMutableList()
) {
    val scrollState = rememberScrollState()
    val centerPosition = remember {
        mutableStateOf(Pair(0, "0"))
    }
    val centerPositionOfDot = remember {
        mutableIntStateOf(0)
    }


    Spacer(modifier = Modifier.height(16.dp))
    Divider(color = Grey, thickness = 6.dp)
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            ScaleCenterPointer(positionOfDot = {
                centerPositionOfDot.intValue = it
            })
        }
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(top = 8.dp)
                .fillMaxWidth(),
            content = {
                for (i in 0..300) {
                    if (i == 0) list.clear()
                    ScaleLineComponent(index = i, returnIndexAndXPosition = {
                        list.add(it.first)
                        centerPosition.value = it

                        // Calculation
                        val closetValue = list.findClosest(centerPositionOfDot.intValue)
                        val index = list.indexOf(closetValue)
                        matchingPosition(index)
                        if (list.size > 300) {
                            list.removeAt(0)
                        }
                    })
                }
            }
        )
    }
}

@Composable
fun ScaleLineComponent(
    index: Int,
    returnIndexAndXPosition: (Pair<Int, String>) -> Unit,
) {
    val isDivisibleBy10 = index % 10 == 0

    Column {
        Canvas(
            modifier = Modifier
                .padding(4.dp)
                .height(100.dp)
                .width(3.dp)
                .onGloballyPositioned {
                    returnIndexAndXPosition(Pair(it.positionInRoot().x.toInt(), index.toString()))
                },
        ) {
            drawLine(
                color = DarkGrey,
                start = Offset(0f, 0f),
                end = Offset(0f, if (isDivisibleBy10) size.height * 0.7f else size.height * 0.2f),
                strokeWidth = if (isDivisibleBy10) size.width else size.width * 0.3f
            )
        }

        Text(
            text = if (isDivisibleBy10) "$index" else "",
            textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.Monospace),
            color = DarkGrey,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-30).dp)
        )
    }
}

@Composable
fun ScaleCenterPointer(positionOfDot: (Int) -> Unit) {
    val primaryColor = LightGreen40

    Column {
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .offset(y = (-16).dp)
                .width(3.dp),
        ) {
            drawCircle(
                color = primaryColor,
                radius = 12.dp.toPx(),
            )
        }
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .offset(y = (-16).dp)
                .height(64.dp)
                .width(3.dp)
                .onGloballyPositioned {
                    positionOfDot(it.positionInRoot().x.toInt())
                },
        ) {
            drawLine(
                color = primaryColor,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = size.width
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMeasuringScaleComponent() {
    SmartBMICalculatorTheme {
        MeasuringScaleComponent({})
    }
}