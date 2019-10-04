package com.syanko.simonis.ui.main.inspection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.entity.InspectionSchedule
import com.syanko.simonis.domain.interactor.MenuInterActor
import com.syanko.simonis.ui.main.MainViewObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InspectionViewModel @Inject constructor(private val menuInterActor: MenuInterActor) :
    ViewModel() {

    private val inspectionsSource = MutableLiveData<List<MainViewObject.InspectionViewObject>>()
    val inspection: LiveData<List<MainViewObject.InspectionViewObject>> = inspectionsSource

    fun loadInspectionByEquipment(id: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = menuInterActor.getInspectionByEquipment(id)
                .mapToViewObject()

            withContext(Dispatchers.Main) {
                inspectionsSource.value = result
            }
        }
    }

    private fun List<InspectionSchedule>.mapToViewObject(): List<MainViewObject.InspectionViewObject> {
        return map {
            MainViewObject.InspectionViewObject(
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

