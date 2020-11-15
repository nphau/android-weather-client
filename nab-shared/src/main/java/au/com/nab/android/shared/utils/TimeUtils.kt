package au.com.nab.android.shared.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    const val PATTERN_E_MMM_dd_yyyy =           // “Sat, Jul 14, 2018”
        "E, dd MMM yyyy"

    /**
     * This number represents the number of milliseconds that have elapsed since January
     * 1st, 1970 at midnight in the GMT time zone.
     * This TimeZone represents the device's current time zone. It provides us with a means
     * of acquiring the offset for local time from a UTC time stamp.
     *
     * The getOffset method returns the number of milliseconds to add to UTC time to get the
     * elapsed time since the epoch for our current time zone. We pass the current UTC time
     * into this method so it can determine changes to account for daylight savings time.
     *
     * UTC time is measured in milliseconds from January 1, 1970 at midnight from the GMT
     * time zone. Depending on your time zone, the time since January 1, 1970 at midnight (GMT)
     * will be greater or smaller. This variable represents the number of milliseconds since
     * January 1, 1970 (GMT) time.
     *
     *  This method simply converts milliseconds to days, disregarding any fractional days
     * Finally, we convert back to milliseconds. This time stamp represents today's date at
     * midnight in GMT time. We will need to account for local time zone offsets when
     * extracting this information from the database.
     */
    val timestamp: Long
        get() {
            val utcNowMillis = System.currentTimeMillis()
            val currentTimeZone = TimeZone.getDefault()
            val gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis).toLong()
            val timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis
            val daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis)
            return TimeUnit.DAYS.toMillis(daysSinceEpochLocal)
        }

    @JvmStatic
    fun toDate(timestamp: Long, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date(timestamp)).toString()
    }

}