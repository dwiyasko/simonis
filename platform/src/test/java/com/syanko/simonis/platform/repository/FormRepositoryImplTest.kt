package com.syanko.simonis.platform.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.syanko.simonis.domain.repository.FormRepository
import com.syanko.simonis.platform.api.FormApi
import com.syanko.simonis.platform.testDoubles.createForm
import com.syanko.simonis.platform.testDoubles.newFormResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FormRepositoryImplTest {

    @Test
    fun test() {
        val equipmentId = 1
        val formResponse = newFormResponse()
        val formApi: FormApi = mock {
            onBlocking { getFormByInspection(equipmentId) } doReturn formResponse
        }
        val repository: FormRepository = FormRepositoryImpl(formApi)

        val expectedResult = listOf(createForm())
        val result = runBlocking { repository.getFormByInspection(equipmentId) }

        assertEquals(expectedResult, result)
    }
}