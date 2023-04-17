package id.heycoding.sholehapp.utils

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    fun rupiahFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price).replace(",", ".") + ",-"
    }

    fun riceFormat(rice: Double): String {
        return "$rice Liter"
    }

    fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", currentLocale)
        return formatter.format(time)
    }

    fun dateFormat(createdDate: String): String {
        val date: Date?
        val currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", currentLocale)
        val formatterView = SimpleDateFormat("EEE, dd MMM yyyy", currentLocale)
        date = inputDateFormat.parse(createdDate)
        return formatterView.format(date)
    }
}