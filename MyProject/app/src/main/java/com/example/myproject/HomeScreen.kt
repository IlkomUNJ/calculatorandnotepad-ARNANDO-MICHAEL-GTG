package com.example.myproject

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToCalculator: () -> Unit,
    onNavigateToNotepad: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Multi-App Project",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onNavigateToCalculator,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Calculator 🧮", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToNotepad,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Notepad 🗒️", fontSize = 20.sp)
        }
    }
}