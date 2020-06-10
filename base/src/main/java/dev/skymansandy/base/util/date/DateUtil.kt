package dev.skymansandy.base.util.date

import java.util.*

object DateUtil {

    fun getDateResetToMidnight(cal: Calendar): Calendar {
        cal.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal
    }

    fun getDateResetToLast(cal: Calendar): Calendar {
        cal.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return cal
    }
}