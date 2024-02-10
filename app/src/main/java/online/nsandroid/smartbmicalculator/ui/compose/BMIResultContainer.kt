package online.nsandroid.smartbmicalculator.ui.compose

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.nsandroid.smartbmicalculator.R
import online.nsandroid.smartbmicalculator.model.BMIStatus
import online.nsandroid.smartbmicalculator.ui.theme.DarkGrey
import online.nsandroid.smartbmicalculator.ui.theme.Grey
import online.nsandroid.smartbmicalculator.ui.theme.LightBlue40
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40
import online.nsandroid.smartbmicalculator.ui.theme.LightRed40
import online.nsandroid.smartbmicalculator.ui.util.calculateBMI
import online.nsandroid.smartbmicalculator.ui.util.calculateBMIStatus
import online.nsandroid.smartbmicalculator.ui.util.getMaxHealthWeight
import online.nsandroid.smartbmicalculator.ui.util.getMinHealthWeight
import online.nsandroid.smartbmicalculator.ui.util.getUserAgeCategory
import online.nsandroid.smartbmicalculator.ui.util.getUserCategory
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel

@Composable
internal fun BMIResultContainer(viewModel: BmiCalculatorViewModel, shareYourBMI: (Rect) -> Unit) {
    val userData = viewModel.userData.collectAsState()
    val bmiValue: Double = userData.value.calculateBMI()
    val bmiStatus: BMIStatus = bmiValue.calculateBMIStatus()
    var composableBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.onGloballyPositioned {
            composableBounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.boundsInWindow()
            } else {
                it.boundsInRoot()
            }
        }) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
                Text(
                    text = stringResource(id = R.string.your_bmi_is),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$bmiValue",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 104.sp,
                    color = when (bmiStatus) {
                        BMIStatus.HealthyWeight -> LightGreen40
                        BMIStatus.UnderWeight -> LightBlue40
                        else -> LightRed40
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "0", color = DarkGrey)
                        Text(text = "18.5", color = DarkGrey)
                        Text(text = "24.9", color = DarkGrey)
                        Text(text = "30+", color = DarkGrey)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Divider(
                        modifier = Modifier
                            .weight(0.93f)
                            .shadow(elevation = 4.dp),
                        color = LightBlue40,
                        thickness = 6.dp
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(elevation = 4.dp),
                        color = LightGreen40,
                        thickness = 6.dp
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(elevation = 16.dp),
                        color = LightRed40,
                        thickness = 6.dp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Under weight", color = LightBlue40)
                    Text(
                        text = "Healthy",
                        color = LightGreen40,
                        modifier = Modifier.offset(x = (-16).dp)
                    )
                    Text(text = "Obesity", color = LightRed40)
                }
                Spacer(modifier = Modifier.height(64.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Grey,
                        contentColor = Color.Black,
                    )
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your BMI is $bmiValue, indicating your weight is in the ${
                            bmiStatus.getUserCategory()
                        } category for ${userData.value.getUserAgeCategory()} of your height.",
                        modifier = Modifier
                            .padding(12.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "For your height, a normal weight range would be from ${
                            userData.value.height.getMinHealthWeight()
                        } to ${userData.value.height.getMaxHealthWeight()} kilograms.",
                        modifier = Modifier
                            .padding(12.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Maintaining a healthy weight may reduce  the risk of chronic diseases  associated with overweight and obesity.",
                        modifier = Modifier
                            .padding(12.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen40),
            shape = RoundedCornerShape(25),
            onClick = { composableBounds?.let { shareYourBMI(it) } },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.share_your_bmi),
                fontSize = 20.sp,
            )
        }
    }
}

