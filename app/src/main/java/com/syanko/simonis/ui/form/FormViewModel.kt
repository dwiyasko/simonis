package com.syanko.simonis.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syanko.simonis.domain.entity.*
import com.syanko.simonis.domain.interactor.FormInterActor
import com.syanko.simonis.ui.main.MainViewObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FormViewModel @Inject constructor(private val formInterActor: FormInterActor) : ViewModel() {
    private val formSource: MutableLiveData<List<MainViewObject.FormViewObject>> = MutableLiveData()
    val forms: LiveData<List<MainViewObject.FormViewObject>> = formSource

    fun loadFormByScheduleId(id: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val result = formInterActor.getFormByInspection(id)
                .mapToFormViewObject(id)

            withContext(context = Dispatchers.Main) {
                formSource.value = result
            }
        }
    }
}

fun List<Form>.mapToFormViewObject(inspectionId: Int): List<MainViewObject.FormViewObject> {
    return map {
        MainViewObject.FormViewObject(
            it.id,
            inspectionId,
            it.title,
            it.description,
            it.periodId,
            it.periodName,
            it.substationId,
            it.substationName,
            it.voltageId,
            it.voltageName,
            it.bayId,
            it.bayName,
            it.sections.mapToSectionViewObject(it.title)
        )
    }
}

fun List<Section>.mapToSectionViewObject(formTitle: String): List<MainViewObject.SectionViewObject> {
    return map { section ->
        MainViewObject.SectionViewObject(
            section.id,
            section.formId,
            section.parent,
            formTitle,
            section.title,
            section.fields.mapToFormFieldViewObject(section.title, formTitle),
            section.children.mapToSectionViewObject(formTitle)
        )
    }
}

fun List<FormField>.mapToFormFieldViewObject(
    formTitle: String,
    sectionTitle: String
): List<MainViewObject.FormFieldViewObject> {
    return map { formField ->
        MainViewObject.FormFieldViewObject(
            formField.id,
            formField.label,
            formField.type,
            formField.order,
            formField.description,
            formTitle,
            sectionTitle,
            formField.fieldSections.mapToFieldSectionViewObject()
        )
    }
}

fun List<FormFieldSection>.mapToFieldSectionViewObject(): List<FieldSectionViewObject> {
    return map { fieldSection ->
        FieldSectionViewObject(
            fieldSection.id,
            fieldSection.formFieldId,
            fieldSection.label,
            fieldSection.type,
            fieldSection.options.mapToFieldOptionViewObject()
        )
    }
}

fun List<FieldOption>.mapToFieldOptionViewObject(): List<FieldOptionViewObject> {
    return map {
        FieldOptionViewObject(
            it.id,
            it.value,
            it.formFieldSectionId
        )
    }
}
