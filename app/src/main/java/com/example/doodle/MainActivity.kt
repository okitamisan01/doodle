package com.example.doodle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.doodle.ui.theme.DoodleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoodleTheme {
                DoodleApp()
            }
        }
    }
}

@Composable
fun DoodleApp() {
    var currentSettings by remember {
        mutableStateOf(BrushSettings())
    }

    val strokes = remember {
        mutableStateListOf<Stroke>()
    }

    Column(Modifier.fillMaxSize()) {
        ToolPanel(
            settings = currentSettings,
            onSettingsChange = { newSettings: BrushSettings ->
                currentSettings = newSettings
            },
            onClearCanvas = {
                strokes.clear()
            },
            modifier = Modifier.weight(0.25f)
        )

        DrawingCanvas(
            strokes = strokes,
            currentSettings = currentSettings,
            onNewStroke = { newStroke ->
                strokes.add(newStroke)
            },
            modifier = Modifier.weight(0.75f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DoodleAppPreview() {
    DoodleTheme {
        DoodleApp()
    }
}
