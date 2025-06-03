package com.example.holaa_trendy_wear_os.complication

import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import java.util.Calendar

class MainComplicationService : SuspendingComplicationDataSourceService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        if (type != ComplicationType.SHORT_TEXT) {
            return null
        }
        return createComplicationData("Mon", "Monday")
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> createComplicationData("Dom", "Domingo")
            Calendar.MONDAY -> createComplicationData("Lun", "Lunes")
            Calendar.TUESDAY -> createComplicationData("Mar", "Martes")
            Calendar.WEDNESDAY -> createComplicationData("Mie", "Miercoles")
            Calendar.THURSDAY -> createComplicationData("Jue", "Jueves")
            Calendar.FRIDAY -> createComplicationData("Vie", "Viernes")
            Calendar.SATURDAY -> createComplicationData("Sab", "Sabado")
            else -> throw IllegalArgumentException("too many days")
        }
    }

    private fun createComplicationData(text: String, contentDescription: String) =
        ShortTextComplicationData.Builder(
            text = PlainComplicationText.Builder(text).build(),
            contentDescription = PlainComplicationText.Builder(contentDescription).build()
        ).build()
}