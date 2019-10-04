package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.Form
import com.syanko.simonis.domain.entity.FormResultEntity
import com.syanko.simonis.domain.entity.FormValueEntity

interface FormRepository {
    suspend fun getFormByInspection(id: Int): List<Form>
    suspend fun saveFormSession(form: FormResultEntity): Long
    suspend fun deleteFormSession(formSessionId: Long): Int
    suspend fun saveFormValue(value: List<FormValueEntity>): Long
}