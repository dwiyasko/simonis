package com.syanko.simonis.platform.repository

import com.syanko.simonis.domain.entity.Equipment
import com.syanko.simonis.domain.repository.EquipmentRepository
import com.syanko.simonis.platform.api.EquipmentApi

class EquipmentRepositoryImpl(private val equipmentApi: EquipmentApi) : EquipmentRepository {
    override suspend fun getEquipmentByGroup(id: Int): List<Equipment> {
        return equipmentApi.getEquipmentByGroupId(id).data.map {
            Equipment(it.id, it.groupId, it.name, it.breadCrumbs)
        }
    }
}