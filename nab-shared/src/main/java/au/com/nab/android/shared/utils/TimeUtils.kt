package au.com.nab.android.shared.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    const val PATTERN_E_MMM_dd_yyyy =           // “Sat, Jul 14, 2018”
        "E, dd MMM yyyy"

    @JvmStatic
    fun toDate(timestamp: Long, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date(timestamp)).toString()
    }

}