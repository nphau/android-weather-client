package au.com.nab.android.data.entities

import androidx.room.Entity
import au.com.nab.android.domain.entities.ICityModel
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "cities")
data class CityEntity(
    @field:SerializedName("id")
    override val id: Int? = 0,
    @field:SerializedName("country")
    override val country: String? = "",
    val query: String? = ""
) : ICityModel