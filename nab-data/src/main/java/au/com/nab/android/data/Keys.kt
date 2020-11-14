package au.com.nab.android.data

object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}