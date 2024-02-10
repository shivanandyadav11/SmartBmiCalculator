package online.nsandroid.smartbmicalculator.ui.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.nsandroid.smartbmicalculator.R
import online.nsandroid.smartbmicalculator.ui.theme.Grey
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40

@Composable
internal fun BMICalculatorContainer() {
    val items = remember {
        listOf("Male", "Female")
    }

    val measureItems = remember {
        listOf("Centimeter")
    }
    var expanded by remember { mutableStateOf(false) }
    var meterSelected by remember { mutableStateOf(true) }
    var text by rememberSaveable { mutableStateOf("0") }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val length = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.calorie_calculator_new_line),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            TextSwitch(
                selectedIndex = selectedIndex,
                items = items,
                onSelectionChange = {
                    selectedIndex = it
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.age),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(128.dp)
                .background(Grey),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = stringResource(id = R.string.height),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )

            val iconId = "linkIcon"
            val inlineIcon = InlineTextContent(
                Placeholder(
                    width = 24.sp,
                    height = 24.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Bottom,
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = null,
                    tint = LightGreen40,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 4.dp),
                )
            }
            val annotatedText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = LightGreen40,
                        fontSize = 16.sp,
                    ),
                ) { append(if (meterSelected) measureItems[0] else measureItems[1]) }
                appendInlineContent(iconId)
            }
            Box {
                Row {
                    Text(
                        text = "${length.intValue}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = LightGreen40,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = annotatedText,
                        inlineContent = mapOf(iconId to inlineIcon),
                        modifier = Modifier.clickable { expanded = !expanded })
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(measureItems[0]) },
                            onClick = {
                                expanded = false
                                meterSelected = true
                            }
                        )
                    }
                }
            }
        }
        MeasuringScaleComponent( matchingPosition = {
            length.intValue = it
        }
        )
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Composable
private fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {

    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFDDF7DD))
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // This is for shadow layer matching white background
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )

            Row(modifier = Modifier
                .fillMaxWidth()

                .drawWithContent {

                    // This is for setting black tex while drawing on white background
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.Black,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Color.White,
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }

                }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Icon(
                                modifier = Modifier.padding(top = 4.dp),
                                painter = painterResource(id = if (index == 0) R.drawable.baseline_male_24 else R.drawable.baseline_female_24),
                                contentDescription = null,
                                tint = LightGreen40
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = text,
                                fontSize = 20.sp,
                                color = LightGreen40
                            )
                        }
                    }
                }
            }
        }
    }
}