package au.com.nab.android.domain.entities

data class City(
    override val id: Int?,
    override val country: String? = null,
    val name: String?,
    val coord: Coord? = null,
    val population: Int? = null
) : ICityModel