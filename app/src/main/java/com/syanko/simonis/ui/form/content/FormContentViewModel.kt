package com.syanko.simonis.ui.form.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.entity.FormValueEntity
import com.syanko.simonis.domain.interactor.FormInterActor
import com.syanko.simonis.ui.main.MainViewObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FormContentViewModel @Inject constructor(
    private val formInterActor: FormInterActor
) : ViewModel() {

    private val formContentSource: MutableLiveData<MainViewObject.FormFieldViewObject> =
        MutableLiveData()
    val formContent: LiveData<MainViewObject.FormFieldViewObject> = formContentSource

    fun saveFormContent(values: List<FormValueView>) {
        val valueEntities = values.map {
            FormValueEntity(
                it.formResultId,
                it.formFieldSectionId,
                it.type,
                it.value
            )
        }

        viewModelScope.launch(context = Dispatchers.IO) {
            formInterActor.saveFormValue(valueEntities)
        }
    }
}

data class FormResultView(
    val inspectionId: Int,
    val formId: Int,
    val giId: Int,
    val voltId: Int,
    val bayId: Int,
    val values: List<FormValueView>
)

data class FormValueView(
    val formResultId: Int,
    val formFieldSectionId: Int,
    val type: Int,
    val value: String
)

