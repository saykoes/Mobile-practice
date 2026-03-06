package ci.nsu.moble.main.ui.screens
import ads_mobile_sdk.h5
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ci.nsu.moble.main.viewmodel.CalcViewModel
import ci.nsu.moble.main.viewmodel.ColorPickerViewModel



@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val vm: CalcViewModel = viewModel()

    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("step1") { Step1Screen(navController, vm) }
        composable("step2") { Step2Screen(navController, vm) }
        composable("result") { ResultScreen(navController, vm) }
        composable("history") { HistoryScreen(vm) }
    }
}

@Composable
fun MainScreen(x0: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun Step1Screen(navController: NavController, vm: CalcViewModel) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Этап 1: Параметры", style = MaterialTheme.typography.h5)
        OutlinedTextField(value = vm.depositInput, onValueChange = { vm.depositInput = it }, label = { Text("Взнос") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = vm.monthsInput, onValueChange = { vm.monthsInput = it }, label = { Text("Срок (мес)") }, modifier = Modifier.fillMaxWidth())

        Row(Modifier.fillMaxWidth()) {
            TextButton(onClick = { navController.popBackStack() }) { Text("В НАЧАЛО") }
            Spacer(Modifier.weight(1f))
            Button(onClick = { navController.navigate("step2") }, enabled = vm.depositInput.isNotBlank() && vm.monthsInput.isNotBlank()) { Text("ДАЛЕЕ") }
        }
    }
}

@Composable
fun Step2Screen(navController: NavController, vm: CalcViewModel) {
    val rates = vm.getSuggestedRates()
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Этап 2: Ставка и пополнение", style = MaterialTheme.typography.h5)

        if (rates.isEmpty()) Text("Ошибка: укажите срок на шаге 1", color = Color.Red)

        // Выбор ставки из доступных
        rates.forEach { rate ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = vm.selectedRate == rate, onClick = { vm.selectedRate = rate })
                Text("Ставка $rate%")
            }
        }

        OutlinedTextField(value = vm.monthlyAddInput, onValueChange = { vm.monthlyAddInput = it }, label = { Text("Ежемесячное пополнение") }, modifier = Modifier.fillMaxWidth())

        Row(Modifier.fillMaxWidth()) {
            TextButton(onClick = { navController.popBackStack() }) { Text("НАЗАД") }
            Spacer(Modifier.weight(1f))
            Button(onClick = { vm.calculate(); navController.navigate("result") }) { Text("РАССЧИТАТЬ") }
        }
    }
}