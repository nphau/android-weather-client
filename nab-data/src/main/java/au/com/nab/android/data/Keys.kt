package au.com.nab.android.data

object Keys {

    const val QUERY = "q"
    const val CNT = "cnt"
    const val UNIT = "units"
    const val APP_ID = "appid"
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String

}