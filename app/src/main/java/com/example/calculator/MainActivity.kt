package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge layout support
        setContent {
            CalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display Input and Result
        Text(
            text = input,
            fontSize = 40.sp, // Bigger font size for input
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.End,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp)) // Add space between input and result

        Text(
            text = result,
            fontSize = 48.sp, // Bigger font size for result
            fontWeight = FontWeight.Bold,
            color = Color.Black, // Green color for result
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)), // Add background
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons Layout
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        // Display the rows of buttons
        for (row in buttons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (label in row) {
                    CalculatorButton(label) {
                        when (label) {
                            "=" -> {
                                try {
                                    result = ExpressionBuilder(input).build().evaluate().toString()
                                } catch (e: Exception) {
                                    result = "Error"
                                }
                            }
                            "C" -> {
                                input = ""
                                result = ""
                            }
                            else -> {
                                input += label
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(12.dp)
            .size(70.dp), // Bigger button size
        shape = RoundedCornerShape(16.dp), // Rounded button shape
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Vibrant color for buttons
    ) {
        Text(
            text = label,
            fontSize = 32.sp, // Bigger text inside button
            fontWeight = FontWeight.Bold,
            color = Color.White // White text for better contrast
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}
