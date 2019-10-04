package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.Equipment

interface EquipmentRepository {
    suspend fun getEquipmentByGroup(id: Int): List<Equipment>
}