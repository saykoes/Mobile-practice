package ci.nsu.moble.main.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ci.nsu.moble.main.viewmodel.ColorPickerViewModel


@Composable
fun ColorPickerScreen(
    colorViewModel: ColorPickerViewModel = viewModel()
) {
    // subscribe to colorViewModel.uiState
    val uiState by colorViewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Color Preview
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(uiState.color, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = uiState.hexCode,
                color = if ((uiState.red + uiState.green + uiState.blue) > 480) Color.Black else Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sliders
        ColorSlider(name = "Red", value = uiState.red, onValueChange = {colorViewModel.update(red = it.toInt())})
        ColorSlider(name = "Green", value = uiState.green, onValueChange = {colorViewModel.update(green = it.toInt())})
        ColorSlider(name = "Blue", value = uiState.blue, onValueChange = {colorViewModel.update(blue = it.toInt())})

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { colorViewModel.generateRandomColor() }) {
            Text("Случайный цвет")
        }
    }
}

@Composable
fun ColorSlider(name: String, value: Int, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text("$name: $value")
        Slider(
            value = value.toFloat(),
            onValueChange = onValueChange,
            valueRange = 0f..255f
        )
    }
}