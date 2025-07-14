package com.example.restaurantorderapp

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*
import androidx.room.Database
import androidx.room.RoomDatabase

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_items")
    suspend fun getAll(): List<MenuItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuItem>)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAll()
}

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
