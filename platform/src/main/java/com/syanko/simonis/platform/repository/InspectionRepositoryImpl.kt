package com.syanko.simonis.platform.repository

import android.annotation.SuppressLint
import com.syanko.simonis.domain.entity.InspectionSchedule
import com.syanko.simonis.domain.repository.InspectionRepository
import com.syanko.simonis.platform.api.InspectionApi
import java.text.SimpleDateFormat
import java.util.*

class InspectionRepositoryImpl(val inspectionApi: InspectionApi) : InspectionRepository {
    override suspend fun getInspectionByEquipment(id: Int): List<InspectionSchedule> {
        return inspectionApi.getInspectionByEquipment(id).data.map {
            InspectionSchedule(
                it.id,
                it.title,
                it.description,
                it.period,
                it.time,
                it.equipmentName
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun String.convertToDate(): Date? {
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
    return dateFormatter.parse(this)
}