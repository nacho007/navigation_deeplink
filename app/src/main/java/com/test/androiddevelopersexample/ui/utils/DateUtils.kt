package com.test.androiddevelopersexample.ui.utils

import android.content.Context
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.ACTIVITY_DATE_FORMAT
import com.test.androiddevelopersexample.constants.CRYPTO_CHART_DATE_FORMAT_HOUR_OF_DAY
import com.test.androiddevelopersexample.constants.CRYPTO_CHART_DATE_FORMAT_MONTH_OF_YEAR
import com.test.androiddevelopersexample.constants.HOUR_MINUTE_FORMAT
import com.test.androiddevelopersexample.constants.LARGE_DATE_FORMAT
import com.test.androiddevelopersexample.constants.SERVER_DATE_FORMAT
import com.test.androiddevelopersexample.constants.UTC_TIME_ZONE
import com.test.androiddevelopersexample.constants.YEAR_MONTH_DAY_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private var calendar: Calendar? = null
    private val serverDateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
    private val activityFormat = SimpleDateFormat(ACTIVITY_DATE_FORMAT, Locale.getDefault())
    private val hourMinuteFormat = SimpleDateFormat(HOUR_MINUTE_FORMAT, Locale.getDefault())
    private val yearMonthDayFormat = SimpleDateFormat(YEAR_MONTH_DAY_FORMAT, Locale.getDefault())
    private val walletActivityFormat =
        SimpleDateFormat(LARGE_DATE_FORMAT, Locale.getDefault())
    private var cryptoDateFormat = SimpleDateFormat(CRYPTO_CHART_DATE_FORMAT_HOUR_OF_DAY, Locale.getDefault())

    companion object {
        val instance = DateUtils()
    }

    init {
        calendar = Calendar.getInstance()
    }

    fun getDateWithDelayFromString(dateString: String?, releaseDelay: Int): Date {
        val inputDateFormat = serverDateFormat
        inputDateFormat.timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE)

        val d = inputDateFormat.parse(dateString ?: "")
        val dCalendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_TIME_ZONE))
        dCalendar.time = d ?: Date()
        dCalendar.add(Calendar.SECOND, releaseDelay)
        return dCalendar.time
    }

    fun getDateWithReleaseFromDate(date: Date?, releaseTime: Int): Date {
        val dCalendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_TIME_ZONE))
        dCalendar.time = date ?: Date()
        dCalendar.add(Calendar.SECOND, releaseTime)
        return dCalendar.time
    }

    fun getActivityDateFromServerDate(context: Context, dateString: String?): String {
        var str = ""
        try {

            val inputDateFormat = serverDateFormat
            inputDateFormat.timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE)
            val outputFormat = activityFormat

            val d = inputDateFormat.parse(dateString ?: "")
            val dCalendar = Calendar.getInstance()
            dCalendar.time = d ?: Date()

            val calendarYesterday = Calendar.getInstance()
            calendarYesterday.add(Calendar.DAY_OF_YEAR, -1)

            str = if (calendar?.get(Calendar.YEAR) == dCalendar.get(Calendar.YEAR) &&
                calendar?.get(Calendar.DAY_OF_YEAR) == dCalendar.get(Calendar.DAY_OF_YEAR)
            ) {
                hourMinuteFormat.format(d ?: Date())
            } else if (calendarYesterday.get(Calendar.YEAR) == dCalendar.get(Calendar.YEAR) &&
                calendarYesterday.get(Calendar.DAY_OF_YEAR) == dCalendar.get(Calendar.DAY_OF_YEAR)
            ) {
                context.getString(R.string.mobile_yesterday)
            } else {
                outputFormat.format(d ?: Date())
            }

        } catch (ex: Exception) {
            if (!dateString.isNullOrEmpty()) {
                str = dateString
            }
            ex.printStackTrace()
        }
        return str
    }

    fun getCompleteDateFormat(selectedDate: String?): String {
        val serverDate = serverDateFormat.parse(selectedDate ?: "")
        val date = yearMonthDayFormat.format(serverDate ?: Date())
        val hourMinute = hourMinuteFormat.format(serverDate ?: Date())
        return "$date, $hourMinute"
    }

    fun getWalletActivityFormat(date: String): String {
        val d: Date?
        d = serverDateFormat.parse(date) ?: Date()
        return walletActivityFormat.format(d)
    }

    fun getCryptoChartDateFormatDay(date: Date): String {
        cryptoDateFormat = SimpleDateFormat(CRYPTO_CHART_DATE_FORMAT_HOUR_OF_DAY, Locale.getDefault())
        return cryptoDateFormat.format(date)
    }

    fun  getCryptoChartDateFormatYear(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        cryptoDateFormat =
            if(calendar.get(Calendar.MONTH) == 0  && calendar.get(Calendar.DAY_OF_MONTH) < 10)
                SimpleDateFormat("yyyy", Locale.getDefault())
            else
                SimpleDateFormat(CRYPTO_CHART_DATE_FORMAT_MONTH_OF_YEAR, Locale.getDefault())
        return cryptoDateFormat.format(date)
    }
}