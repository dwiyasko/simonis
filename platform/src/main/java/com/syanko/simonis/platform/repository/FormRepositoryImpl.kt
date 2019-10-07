package com.syanko.simonis.platform.repository

import com.syanko.simonis.domain.entity.*
import com.syanko.simonis.domain.entity.Form
import com.syanko.simonis.domain.repository.FormRepository
import com.syanko.simonis.platform.api.FormApi
import com.syanko.simonis.platform.db.DaoProvider
import com.syanko.simonis.platform.db.entity.FormResult
import com.syanko.simonis.platform.db.entity.Value
import com.syanko.simonis.platform.response.*

class FormRepositoryImpl(private val formApi: FormApi, private val daoProvider: DaoProvider) :
    FormRepository {
    override suspend fun getFormByInspection(id: Int): List<Form> {
        return formApi.getFormByInspection(id).mapToForm()
    }

    override suspend fun saveFormSession(form: FormResultEntity): Long {
        val formResult = form.mapToFormResult()
        return daoProvider.formDao().saveFormSession(formResult)
    }

    override suspend fun deleteFormSession(formSessionId: Long): Int {
        return daoProvider.formDao().deleteFormSession(formSessionId)
    }

//    override suspend fun saveFormValue(value: List<FormValueEntity>): Long {
//        val formValue = value.map {
//            it.mapToFormValue()
//        }
//
//        return daoProvider.formValueDao().saveFormValue(formValue)
//    }

    //region private method
    private fun FormResultEntity.mapToFormResult(): FormResult {
        return FormResult(
            formId = formId,
            userId = userId,
            inspectionId = inspectionId,
            giId = substationId,
            voltId = voltageId,
            bayId = bayId,
            sectionId = 0
        )
    }

    private fun FormValueEntity.mapToFormValue(): Value {
        return Value(
            formResultId,
            formFieldSectionId,
            value,
            type
        )
    }
    //endregion
}

private fun FormResponse.mapToForm(): List<Form> {
    return form.map {
        Form(
            it.id,
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
            it.sections.mapToSectionEntity()
        )
    }
}

private fun List<SectionResponse>.mapToSectionEntity(): List<Section> {
    return map {
        Section(
            it.id,
            it.formId,
            it.parent,
            it.title,
            it.fields.mapToFieldEntity(),
            it.children.mapToSectionEntity()
        )
    }
}

private fun List<FormFieldResponse>.mapToFieldEntity(): List<FormField> {
    return map {
        FormField(
            it.id,
            it.label,
            it.type,
            it.order,
            it.description,
            it.fieldSections.mapToFieldSectionEntity()
        )
    }
}

private fun List<FieldSectionResponse>.mapToFieldSectionEntity(): List<FormFieldSection> {
    return map {
        FormFieldSection(
            it.id,
            it.formFieldId,
            it.label,
            convertPrimitiveType(it.type),
            it.options.mapToOptionEntity()
        )
    }
}

private fun convertPrimitiveType(type: Int): FormType {
    return when (type) {
        1 -> FormType.TEXT
        2 -> FormType.RADIO
        3 -> FormType.CHECKBOX
        4 -> FormType.NUMBER
        5 -> FormType.DATE
        else -> FormType.UNKNOWN
    }
}

private fun List<FieldOptionResponse>.mapToOptionEntity(): List<FieldOption> {
    return map {
        FieldOption(it.id, it.value, it.formFieldSectionId)
    }
}

