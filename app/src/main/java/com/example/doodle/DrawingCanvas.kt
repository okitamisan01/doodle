package com.example.doodle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.drawscope.Stroke as DrawStroke

@Composable
fun DrawingCanvas(
    strokes: List<Stroke>,
    currentSettings: BrushSettings,
    onNewStroke: (Stroke) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDrawingPath = remember { mutableStateListOf<Offset>() }

    Box(
        modifier = modifier
            .clipToBounds()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(currentSettings) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentDrawingPath.clear()
                            currentDrawingPath.add(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentDrawingPath.add(change.position)
                        },
                        onDragEnd = {
                            if (currentDrawingPath.isNotEmpty()) {
                                val newStroke = Stroke(
                                    points = currentDrawingPath.toList(),
                                    settings = currentSettings
                                )
                                onNewStroke(newStroke)
                            }
                            currentDrawingPath.clear()
                        }
                    )
                }
        ) {
            val allStrokes = strokes + Stroke(currentDrawingPath.toList(), currentSettings)

            allStrokes.forEach { stroke ->

                if (stroke.points.size > 1) {
                    val path = Path().apply {
                        moveTo(stroke.points.first().x, stroke.points.first().y)
                        for (i in 1 until stroke.points.size) {
                            lineTo(stroke.points[i].x, stroke.points[i].y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = stroke.settings.color,
                        style = DrawStroke(
                            width = stroke.settings.strokeWidth,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}
