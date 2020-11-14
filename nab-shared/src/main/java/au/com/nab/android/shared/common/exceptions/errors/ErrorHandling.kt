package au.com.nab.android.shared.common.exceptions.errors

import org.json.JSONObject
import java.net.HttpURLConnection

object ErrorHandling {

    @JvmStatic
    fun parseServerError(error: String?): Pair<Int, String> {
        var errorMessage = ""
        var errorCode = -1
        try {
            if (error != null) {
                val jsonObject = JSONObject(error)
                if (jsonObject.has("message")) {
                    errorMessage = jsonObject.getString("message")
                } else if (jsonObject.has("errors")) {
                    val errors = jsonObject.getJSONObject("errors")
                    if (errors.has("message"))
                        errorMessage = errors.getString("message")
                } else if (jsonObject.has("")) {
                    errorMessage = jsonObject.getString("")
                }
                if (jsonObject.has("status"))
                    errorCode = jsonObject.getInt("status")

                if (jsonObject.has("code"))
                    errorCode = jsonObject.getInt("code")

                if (jsonObject.has("cod"))
                    errorCode = jsonObject.getString("cod").toIntOrNull()
                        ?: HttpURLConnection.HTTP_NO_CONTENT

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(errorCode, errorMessage)
    }
}