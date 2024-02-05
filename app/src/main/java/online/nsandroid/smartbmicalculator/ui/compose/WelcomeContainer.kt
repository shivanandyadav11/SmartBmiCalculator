package online.nsandroid.smartbmicalculator.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.nsandroid.smartbmicalculator.R
import online.nsandroid.smartbmicalculator.ui.theme.LightGreen40

@Composable
internal fun WelcomeContainer(onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .background(Color.White)) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(id = R.string.calorie_calculator_heading),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.calorie_calculator_para)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.smartbmi),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen40),
            shape = RoundedCornerShape(25),
            onClick = { onClick() },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.calorie_calculator_heading),
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.padding(top = 4.dp),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}