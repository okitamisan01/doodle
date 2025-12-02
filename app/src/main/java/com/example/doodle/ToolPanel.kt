package com.example.doodle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToolPanel(
    settings: BrushSettings,
    onSettingsChange: (BrushSettings) -> Unit,
    onClearCanvas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Stroke Width: ${settings.strokeWidth.toInt()}")
        Slider(
            value = settings.strokeWidth,
            onValueChange = { onSettingsChange(settings.copy(strokeWidth = it)) },
            valueRange = 1f..50f
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val colors = listOf(
                Color.Black, Color.Red, Color.Green, Color.Blue,
                Color.Yellow, Color.Magenta
            )
            colors.forEach { color ->
                ColorCircle(color = color) {
                    onSettingsChange(settings.copy(color = color))
                }
            }
            Button(onClick = { onSettingsChange(settings.copy(color = Color.White)) }) {
                Text("Eraser")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClearCanvas,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Clear Canvas")
        }
    }
}

@Composable
fun ColorCircle(color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
    )
}
