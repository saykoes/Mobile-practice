package ci.nsu.moble.main.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ColorUiState(
    val red: Int = 128,
    val green: Int = 128,
    val blue: Int = 128
) {
    val color: Color get() = Color(red, green, blue)

    // Форматирование HEX для корректного отображения
    val hexCode: String get() = String.format("#%02X%02X%02X", red, green, blue)
}

class ColorPickerViewModel : ViewModel() {
    // Using MutableStateFlow for local UiState
    private val _uiState = MutableStateFlow(ColorUiState())
    // Public StateFlow for UI
    val uiState: StateFlow<ColorUiState> = _uiState.asStateFlow()

    fun update(red: Int = uiState.value.red, green: Int = uiState.value.green, blue: Int = uiState.value.blue) {
        _uiState.update { it.copy(red = red, green = green, blue = blue) }
    }

    fun generateRandomColor() {
        _uiState.update {
            ColorUiState(
                red = (0..255).random(),
                green = (0..255).random(),
                blue = (0..255).random()
            )
        }
    }
}