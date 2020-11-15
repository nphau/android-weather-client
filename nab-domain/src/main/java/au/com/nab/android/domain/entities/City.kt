package au.com.nab.android.domain.entities

data class City(
    val id: Int?,
    val name: String?,
    val coord: Coord? = null,
    val country: String? = null,
    val population: Int? = null
)