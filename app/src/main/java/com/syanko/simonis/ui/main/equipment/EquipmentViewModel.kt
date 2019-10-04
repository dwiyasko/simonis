package com.syanko.simonis.ui.main.equipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.entity.Equipment
import com.syanko.simonis.domain.interactor.MenuInterActor
import com.syanko.simonis.ui.main.MainViewObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EquipmentViewModel @Inject constructor(private val menuInterActor: MenuInterActor) :
    ViewModel() {

    private val equipmentSource = MutableLiveData<List<MainViewObject.EquipmentViewObject>>()
    val equipments: LiveData<List<MainViewObject.EquipmentViewObject>> = equipmentSource

    fun loadEquipmentByGroup(id: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = menuInterActor.getEquipmentByGroup(id)
                .mapToEquipmentViewObject()

            withContext(Dispatchers.Main) {
                equipmentSource.value = result
            }
        }
    }

    private fun List<Equipment>.mapToEquipmentViewObject(): List<MainViewObject.EquipmentViewObject> {
        return map {
            MainViewObject.EquipmentViewObject(
                it.id,
                it.groupId,
                it.name,
                it.breadCrumbs
            )
        }
    }

}

