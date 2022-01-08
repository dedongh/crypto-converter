package com.engineerskasa.oparetaconvertor.util

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.room.rxjava3.EmptyResultSetException
import com.engineerskasa.oparetaconvertor.R
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.abs

fun getTimeStamp(): String {
    return DateTimeFormatter
        .ofPattern("HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
}

fun getTimeToSync(startTime: String, endTime: String): Triple<Int, Int, Int> {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
    val startDate = simpleDateFormat.parse(startTime)
    val endDate = simpleDateFormat.parse(endTime)
    val diff: Long = abs(endDate.time - startDate.time)
    var timeInSeconds = (diff / 1000).toInt()
    val hours = timeInSeconds / 3600
    timeInSeconds -= (hours * 3600)
    val minutes = timeInSeconds / 60
    timeInSeconds -= (minutes * 3600)
    val seconds = timeInSeconds
    return Triple(hours, minutes, seconds)
}


fun showErrorMessage(view: View, e: Throwable) {
    var message = ""
    try {
        when (e) {
            is IOException -> {
                message = "No internet connection: ${e.message}"
            }
            is HttpException -> {
                val errorBody = e.response()!!.errorBody()!!.string()
                val jsonObject = JSONObject(errorBody)
                message = when (e.code()) {
                    404 -> "Not found"
                    500 -> "An unexpected error occurred... please try again later"
                    else -> jsonObject.getString("message")
                }
            }
            is EmptyResultSetException -> {
                message = e.message.toString()
            }
        }
    } catch (e1: IOException) {
        e1.printStackTrace()
    } catch (e1: JSONException) {
        e1.printStackTrace()
    } catch (e1: Exception) {
        e1.printStackTrace()
    }
    if (message.isEmpty()) {
        message = "Cannot retrieve info at this time. Please try again later"
    }
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
    val sbView = snackbar.view
    sbView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.purple_700))
    val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackbar.show()
}