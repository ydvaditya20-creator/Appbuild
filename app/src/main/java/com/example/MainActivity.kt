package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            name = "World",
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  HelloWorldScreen(modifier)
}

@Composable
fun HelloWorldScreen(modifier: Modifier = Modifier) {
  var visible by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    delay(200)
    visible = true
  }

  // Elegant deep slate gradient background
  val backgroundGradient = Brush.verticalGradient(
    colors = listOf(
      Color(0xFF0F172A), // Deep Slate Dark
      Color(0xFF1E293B)  // Slate Grey
    )
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(backgroundGradient),
    contentAlignment = Alignment.Center
  ) {
    AnimatedVisibility(
      visible = visible,
      enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)) +
              scaleIn(initialScale = 0.92f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
    ) {
      Card(
        modifier = Modifier
          .padding(24.dp)
          .widthIn(max = 400.dp)
          .testTag("hello_world_card"),
        colors = CardDefaults.cardColors(
          containerColor = Color(0xFF1E293B).copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
      ) {
        Column(
          modifier = Modifier
            .padding(horizontal = 40.dp, vertical = 52.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Decorative minimal studio badge
          Box(
            modifier = Modifier
              .background(Color(0xFF38BDF8), shape = RoundedCornerShape(50))
              .padding(horizontal = 14.dp, vertical = 6.dp)
          ) {
            Text(
              text = "STUDIO",
              color = Color(0xFF0F172A),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 2.5.sp,
              fontFamily = FontFamily.Monospace
            )
          }

          Spacer(modifier = Modifier.height(32.dp))

          // Display Greeting
          Text(
            text = "Hello",
            color = Color(0xFF94A3B8), // Slate-400
            fontSize = 32.sp,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "World!",
            color = Color(0xFFF8FAFC), // Slate-50
            fontSize = 54.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("hello_world_text")
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Aesthetic design accent divider
          Box(
            modifier = Modifier
              .widthIn(max = 80.dp)
              .height(2.dp)
              .background(Color(0xFF38BDF8).copy(alpha = 0.4f))
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  MyApplicationTheme {
    HelloWorldScreen()
  }
}
