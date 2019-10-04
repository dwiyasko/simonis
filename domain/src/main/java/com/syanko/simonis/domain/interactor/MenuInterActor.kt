package com.syanko.simonis.domain.interactor

import com.syanko.simonis.domain.entity.Equipment
import com.syanko.simonis.domain.entity.InspectionSchedule
import com.syanko.simonis.domain.entity.Menu
import com.syanko.simonis.domain.repository.EquipmentRepository
import com.syanko.simonis.domain.repository.GroupRepository
import com.syanko.simonis.domain.repository.InspectionRepository

interface MenuInterActor {
    suspend fun getMenu(id: Int = -1): List<Menu>
    suspend fun getEquipmentByGroup(id: Int): List<Equipment>
    suspend fun getInspectionByEquipment(id: Int): List<InspectionSchedule>
}

class MenuInterActorImpl(
    private val groupRepository: GroupRepository,
    private val equipmentRepository: EquipmentRepository,
    private val inspectionRepository: InspectionRepository
) : MenuInterActor {
    override suspend fun getMenu(id: Int): List<Menu> {
        return groupRepository.getGroupTree()
    }

    override suspend fun getEquipmentByGroup(id: Int): List<Equipment> {
        return equipmentRepository.getEquipmentByGroup(id)
    }

    override suspend fun getInspectionByEquipment(id: Int): List<InspectionSchedule> {
        return inspectionRepository.getInspectionByEquipment(id)
    }
}