package com.syanko.simonis.domain.interactor

import com.syanko.simonis.domain.entity.Form
import com.syanko.simonis.domain.entity.FormResultEntity
import com.syanko.simonis.domain.entity.FormValueEntity
import com.syanko.simonis.domain.repository.FormRepository

interface FormInterActor {
    suspend fun getFormByInspection(id: Int): List<Form>
    suspend fun saveFormSession(form: FormResultEntity): Long
    suspend fun deleteFormSession(id: Long): Int
//    suspend fun saveFormValue(formValues: List<FormValueEntity>): Long
}

class FormInterActorImpl(private val formRepository: FormRepository) : FormInterActor {
    override suspend fun getFormByInspection(id: Int): List<Form> {
        return formRepository.getFormByInspection(id)
    }

    override suspend fun saveFormSession(form: FormResultEntity): Long {
        return formRepository.saveFormSession(form)
    }

//    override suspend fun saveFormValue(formValues: List<FormValueEntity>): Long {
//        return formRepository.saveFormValue(formValues)
//    }

    override suspend fun deleteFormSession(id: Long): Int {
        return formRepository.deleteFormSession(id)
    }
}