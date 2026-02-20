package ci.nsu.moble.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ci.nsu.moble.main.ui.theme.MobilepracticeTheme

import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Content goes under status bar
        setContent {
            // 1. Вызываем Composable и передаем в него ЛЯМБДУ (блок кода)
            MainScreen(onNavigate = { data ->
                // Этот код выполнится ТОЛЬКО когда нажмут кнопку в MainScreen
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("ci.nsu.saikou.EXTRA_DATA", data)
                }
                startActivity(intent)
            })
        }
    }
}

@Composable
fun MainScreen(onNavigate: (String) -> Unit) { // Принимаем функцию как параметр
    var textToPass by remember { mutableStateOf("Привет из главной!") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле ввода, чтобы данные были динамическими
        TextField(
            value = textToPass,
            onValueChange = { textToPass = it },
            label = { Text("Что передать?") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Использование onNavigate
        Button(onClick = {
            // Вызываем переданную функцию и отдаем ей строку
            onNavigate(textToPass)
        }) {
            Text("Перейти во вторую Activity")
        }
    }
}