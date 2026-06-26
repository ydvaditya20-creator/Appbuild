package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
                    HelloWorldScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class GreetingItem(val text: String, val language: String)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HelloWorldScreen(modifier: Modifier = Modifier) {
    // List of international "Hello World" translations
    val greetings = listOf(
        GreetingItem("Hello, World!", "English"),
        GreetingItem("नमस्ते दुनिया!", "Hindi"),
        GreetingItem("¡Hola Mundo!", "Spanish"),
        GreetingItem("Bonjour le monde!", "French"),
        GreetingItem("こんにちは世界!", "Japanese"),
        GreetingItem("Hallo Welt!", "German"),
        GreetingItem("Ciao mondo!", "Italian")
    )

    var currentIndex by remember { mutableStateOf(0) }
    var autoPlay by remember { mutableStateOf(true) }

    // Automatically cycle through greetings
    LaunchedEffect(autoPlay) {
        if (autoPlay) {
            while (true) {
                delay(3500)
                currentIndex = (currentIndex + 1) % greetings.size
            }
        }
    }

    val currentGreeting = greetings[currentIndex]

    // Beautiful atmospheric background gradient depending on light/dark mode
    val backgroundGradient = Brush.radialGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
            MaterialTheme.colorScheme.background
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Disable default huge ripple for subtle full-screen interaction
                onClick = {
                    // Turn off autoplay on manual tap, move to next
                    autoPlay = false
                    currentIndex = (currentIndex + 1) % greetings.size
                }
            )
            .testTag("main_screen_container"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Animated container for seamless fade-in/slide-in transitions
            AnimatedContent(
                targetState = currentGreeting,
                transitionSpec = {
                    if (targetState.text != initialState.text) {
                        (slideInVertically { height -> height / 2 } + fadeIn()) with
                                (slideOutVertically { height -> -height / 2 } + fadeOut()) using
                                SizeTransform(clip = false)
                    } else {
                        fadeIn() with fadeOut()
                    }
                },
                label = "GreetingTransition"
            ) { target ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Large Elegant Header
                    Text(
                        text = target.text,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("hello_world_text")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Soft Language Tag
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = target.language,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))

            // Elegant, minimalist instructions & signature
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(0.5f)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Love icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap anywhere to switch language",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelloWorldScreenPreview() {
    MyApplicationTheme {
        HelloWorldScreen()
    }
}
