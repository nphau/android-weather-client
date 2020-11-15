package au.com.nab.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import au.com.nab.android.data.db.dao.WeathersDao
import au.com.nab.android.data.entities.WeatherEntity
import au.com.nab.android.data.entities.WeatherResponseEntity
import au.com.nab.android.data.entities.WeatherStatus
import au.com.nab.android.data.entities.WeatherTemperature

@Database(
    version = 1,
    exportSchema = false,
    entities = [WeatherEntity::class, WeatherStatus::class, WeatherTemperature::class, WeatherResponseEntity::class]
)
@TypeConverters(DBConverters::class)
abstract class LocalDB : RoomDatabase() {

    abstract fun weatherEntityDao(): WeathersDao

}