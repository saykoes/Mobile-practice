import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Дом", Icons.Default.Home)
    object Settings : Screen("settings", "Настройки", Icons.Default.Settings)
}