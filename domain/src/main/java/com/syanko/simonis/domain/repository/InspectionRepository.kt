package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.InspectionSchedule

interface InspectionRepository {
    suspend fun getInspectionByEquipment(id: Int): List<InspectionSchedule>
}