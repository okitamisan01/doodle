package com.example.doodle

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class BrushSettings(
    val color: Color = Color.Black,
    val strokeWidth: Float = 8f
)

data class Stroke(
    val points: List<Offset>,
    val settings: BrushSettings,
)
