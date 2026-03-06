package ci.nsu.moble.main.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "calculation_history")
data class CalculationRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val initialAmount: Double,
    val months: Int,
    val rate: Double,
    val monthlyAdd: Double,
    val totalAmount: Double,
    val interestEarned: Double,
    val date: Long = System.currentTimeMillis()
)

@Dao
interface CalculationDao {
    @Insert suspend fun insert(record: CalculationRecord)
    @Query("SELECT * FROM calculation_history ORDER BY date DESC")
    fun getAll(): Flow<List<CalculationRecord>>
}

@Database(entities = [CalculationRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): CalculationDao
}