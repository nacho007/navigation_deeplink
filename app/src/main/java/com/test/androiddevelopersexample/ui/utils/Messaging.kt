package com.test.androiddevelopersexample.ui.utils

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.gson.JsonObject
import com.test.androiddevelopersexample.BuildConfig.PROJECT_ID
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Firebase Cloud Messaging (FCM) can be used to send messages to clients on iOS, Android and Web.
 *
 *
 * This sample uses FCM to send two types of messages to clients that are subscribed to the `news`
 * topic. One type of message is a simple notification message (display message). The other is
 * a notification message (display notification) with platform specific customizations, for example,
 * a badge is added to messages that are sent to iOS devices.
 */
object Messaging {

    private const val TAG = "Messaging"
    private const val BASE_URL = "https://fcm.googleapis.com"
    private const val FCM_SEND_ENDPOINT = "/v1/projects/$PROJECT_ID/messages:send"
    private const val MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
    private val SCOPES = arrayOf(MESSAGING_SCOPE)

    /**
     * Retrieve a valid access token that can be use to authorize requests to the FCM REST
     * API.
     *
     * @return Access token.
     * @throws IOException
     */
    // [START retrieve_access_token]
    @Throws(IOException::class)
    fun getAccessToken(context: Context): String {
        val stream = context.assets.open("service-account.json")
        val googleCredential = GoogleCredentials
            .fromStream(stream)
            .createScoped(listOf(*SCOPES))
        val access = googleCredential.refreshAccessToken()
        return access.tokenValue
    }
    // [END retrieve_access_token]
    /**
     * Create HttpURLConnection that can be used for both retrieving and publishing.
     *
     * @return Base HttpURLConnection.
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun getConnection(context: Context): HttpURLConnection {
        // [START use_access_token]
        val url = URL(BASE_URL + FCM_SEND_ENDPOINT)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken(context))
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8")
        return httpURLConnection
        // [END use_access_token]
    }

    /**
     * Send request to FCM message using HTTP.
     *
     * @param fcmMessage Body of the HTTP request.
     * @throws IOException
     */
    @Throws(IOException::class)
    fun sendMessage(context: Context, fcmMessage: JsonObject) {
        try {
            val connection = getConnection(context)
            connection.doOutput = true
            val outputStream = DataOutputStream(connection.outputStream)
            outputStream.writeBytes(fcmMessage.toString())
            outputStream.flush()
            outputStream.close()
            val responseCode = connection.responseCode
            if (responseCode == 200) {
                val response = inputStreamToString(connection.inputStream)
                Log.e(TAG, response)
            } else {
                val response = inputStreamToString(connection.errorStream)
                Log.e(TAG, response)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * Read contents of InputStream into String.
     *
     * @param inputStream InputStream to read.
     * @return String containing contents of InputStream.
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun inputStreamToString(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val scanner = Scanner(inputStream)
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine())
        }
        return stringBuilder.toString()
    }
}