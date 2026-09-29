package com.example.a24012011115_mad_practical_7

import android.util.Log
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpRequest {
    private val TAG = "HttpRequest"

    fun makeServiceCall(reqUrl: String?, token: String? = null): String? {
        var response: String? = null
        try {
            val url = URL(reqUrl)
            val conn = url.openConnection() as HttpURLConnection
            if (token != null) {
                conn.setRequestProperty("Authorization", "Bearer $token")
                conn.setRequestProperty("Content-Type", "application/json")
            }
            conn.requestMethod = "GET"
            response = BufferedInputStream(conn.inputStream).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
        return response
    }

    fun makeHttpRequest(urlString: String): String? {
        return makeServiceCall(urlString)
    }
}
