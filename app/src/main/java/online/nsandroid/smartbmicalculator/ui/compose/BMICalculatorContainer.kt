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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import online.nsandroid.smartbmicalculator.R
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40

@Composable
internal fun BMICalculatorContainer(onCalculateClick: (String, String, Int, Int, Int) -> Unit) {
    val items = remember {
        listOf("Male", "Female")
    }

    val measureItems = remember {
        listOf("Centimeter")
    }
    var expanded by remember { mutableStateOf(false) }
    var meterSelected by remember { mutableStateOf(true) }
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val length = remember {
        mutableIntStateOf(0)
    }

    val weightMeasureItems = remember {
        listOf("Kg")
    }
    var weightExpanded by remember { mutableStateOf(false) }
    var weightSelected by remember { mutableStateOf(true) }
    val weight = remember {
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
        Row {
            OutlinedTextField(
                value = name,
                leadingIcon = { Icon(imageVector = Icons.Default.Face, contentDescription = null) },
                trailingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(.8f)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                label = { Text(text = "Your Name") },
                placeholder = { Text(text = "") },
                onValueChange = {
                    name = it
                }
            )
            OutlinedTextField(
                value = age,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(.25f)
                    .fillMaxWidth(),
                label = { Text(text = "Age") },
                placeholder = { Text(text = "0") },
                onValueChange = {
                    if(it.isDigitsOnly()) {
                        age = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            )
        }
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
        // Start of Weight Region
        Row {
            Text(
                text = stringResource(id = R.string.weight),
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
                ) { append(if (weightSelected) weightMeasureItems[0] else weightMeasureItems[1]) }
                appendInlineContent(iconId)
            }
            Box {
                Row {
                    Text(
                        text = "${weight.intValue}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = LightGreen40,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = annotatedText,
                        inlineContent = mapOf(iconId to inlineIcon),
                        modifier = Modifier.clickable { weightExpanded = !weightExpanded })
                    DropdownMenu(
                        expanded = weightExpanded,
                        onDismissRequest = { weightExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(weightMeasureItems[0]) },
                            onClick = {
                                weightExpanded = false
                                weightSelected = true
                            }
                        )
                    }
                }
            }
        }
        MeasuringScaleComponent( matchingPosition = {
            weight.intValue = it
        }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen40),
            shape = RoundedCornerShape(25),
            onClick = { onCalculateClick(
                name,
                items[selectedIndex],
                if(age.isNotEmpty()) age.toInt() else 0,
                length.intValue,
                weight.intValue
            ) },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.calculate_your_bmi),
                fontSize = 20.sp,
            )
        }
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