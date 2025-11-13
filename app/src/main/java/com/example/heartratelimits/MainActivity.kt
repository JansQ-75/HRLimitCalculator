package com.example.heartratelimits


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heartratelimits.ui.theme.HeartRateLimitsTheme
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeartRateLimitsTheme {Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HeartRateLimits(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun HeartRateLimits(modifier: Modifier = Modifier) {
    var ageInput by remember { mutableStateOf("") } // tilamuuttuja ikää varten
    val age = ageInput.toIntOrNull() ?: 0                   // muutetaan syötetty ikä integeriksi
    val upper = if (age > 0) (220 - age) * 0.85f else 0     // sykkeiden yläraja
    val lower = if (age > 0) (200 - age) * 0.65f else 0     // sykkeiden alaraja
    val df = DecimalFormat("#.##")                  // formatoidaan luvut kahden desimaalin tarkkuudella
    df.roundingMode = RoundingMode.CEILING                  // pyöristetään luvut ylöspäin

    Box(modifier = modifier.fillMaxSize())              // Box -layout
    {
        Image(                                          // taustakuva
            painter = painterResource(id = R.drawable.unicorn),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(                                         // Column -layout laskuria varten
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(                              // tekstin syöttökenttä
                value = ageInput,
                onValueChange = {ageInput = it},
                label = {Text(text="Age")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray),

                )
            Text(
                text = stringResource(R.string.your_heart_rate_limits_are),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.lower_limit_bpm, df.format(lower)),
                fontSize = 18.sp,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.upper_limit_bpm, df.format(upper)),
                fontSize = 18.sp,
                color = Color.White,
            )
        }
        if (age > 0) {
            Text(               // Loppukevennys. Näytetään jos käyttäjä on syöttänyt iän
                text = "Unicorn power: Activated!",
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp)
            )
        }
    }
    

}
