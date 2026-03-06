package ci.nsu.moble.main.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import ci.nsu.moble.main.model.AppDatabase
import ci.nsu.moble.main.model.CalculationRecord
import kotlinx.coroutines.launch

class CalcViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "calc_db").build()
    private val dao = db.dao()

    var depositInput by mutableStateOf("")
    var monthsInput by mutableStateOf("")
    var monthlyAddInput by mutableStateOf("")
    var selectedRate by mutableStateOf(0.0)

    val history = dao.getAll().asLiveData()
    var lastResult by mutableStateOf<CalculationRecord?>(null)

    // Автоматический подбор ставки по условию ТЗ
    fun getSuggestedRates(): List<Double> {
        val m = monthsInput.toIntOrNull() ?: return emptyList()
        return when {
            m < 6 -> listOf(15.0)
            m in 6..11 -> listOf(10.0)
            else -> listOf(5.0)
        }
    }

    fun calculate() {
        val p = depositInput.toDoubleOrNull() ?: 0.0
        val n = monthsInput.toIntOrNull() ?: 0
        val add = monthlyAddInput.toDoubleOrNull() ?: 0.0
        val r = selectedRate / 100 / 12

        // Формула сложного процента с пополнениями
        var total = p
        for (i in 1..n) {
            total = (total + add) * (1 + r)
        }

        lastResult = CalculationRecord(
            initialAmount = p, months = n, rate = selectedRate,
            monthlyAdd = add, totalAmount = total, interestEarned = total - p - (add * n)
        )
    }

    fun saveResult() {
        lastResult?.let { viewModelScope.launch { dao.insert(it) } }
    }
}