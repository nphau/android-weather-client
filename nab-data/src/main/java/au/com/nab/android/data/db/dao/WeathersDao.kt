package au.com.nab.android.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import au.com.nab.android.data.entities.WeatherResponseEntity
import io.reactivex.Single

@Dao
interface WeathersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weathers: WeatherResponseEntity)

    @Query("SELECT * FROM weathersResponse WHERE cityName = :city")
    fun findByCityName(city: String?): Single<WeatherResponseEntity>

}