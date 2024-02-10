package online.nsandroid.smartbmicalculator.ui.compose

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.nsandroid.smartbmicalculator.R
import online.nsandroid.smartbmicalculator.model.BMIStatus
import online.nsandroid.smartbmicalculator.model.UserData
import online.nsandroid.smartbmicalculator.ui.theme.DarkGrey
import online.nsandroid.smartbmicalculator.ui.theme.Grey
import online.nsandroid.smartbmicalculator.ui.theme.LightBlue40
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40
import online.nsandroid.smartbmicalculator.ui.theme.LightRed40
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel
import kotlin.math.pow

@Composable
internal fun BMIResultContainer(viewModel: BmiCalculatorViewModel) {
    val userData = viewModel.userData.collectAsState()
    val bmiValue: Double = calculateBMI(userData.value)
    val bmiStatus: BMIStatus = calculateBMIStatus(bmiValue)
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
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
            Text(text = "Healthy", color = LightGreen40, modifier = Modifier.offset(x = (-16).dp))
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
                    getUserCategory(
                        bmiStatus
                    )
                } category for ${getUserAgeCategory(userData.value)} of your height.",
                modifier = Modifier
                    .padding(12.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "For your height, a normal weight range would be from ${
                    getMinHealthWeight(
                        userData.value.height
                    )
                } to ${getMaxHealthWeight(userData.value.height)} kilograms.",
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
    }
}

fun getMaxHealthWeight(height: Double): Double {
    return Math.round(24.9 * height * 100.0) / 100.0
}

fun getMinHealthWeight(height: Double): Double {
    return Math.round(18.5 * height * 100.0) / 100.0
}

fun getUserAgeCategory(bmiValue: UserData): String {
    return when {
        bmiValue.age < 12 -> "Child"
        bmiValue.age in 12..19 -> "teen"
        bmiValue.age in 20..65 -> "Adult"
        else -> "Old"
    }
}

fun getUserCategory(bmiStatus: BMIStatus): String {
    return when (bmiStatus) {
        BMIStatus.UnderWeight -> "Under Weight"
        BMIStatus.HealthyWeight -> "Normal"
        else -> "over Weight"
    }
}

fun calculateBMIStatus(bmiValue: Double): BMIStatus {
    return when {
        (bmiValue < 18.5) -> BMIStatus.UnderWeight
        (bmiValue >= 18.5 && bmiValue < 25) -> BMIStatus.HealthyWeight
        else -> BMIStatus.OverWeightWeight
    }
}

fun calculateBMI(userData: UserData): Double {
    return Math.round(userData.weight / userData.height.pow(2) * 100.0) / 100.0
}
