package com.syanko.simonis.platform.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.syanko.simonis.domain.repository.GroupRepository
import com.syanko.simonis.platform.api.GroupApi
import com.syanko.simonis.platform.testDoubles.createGroupResponse
import com.syanko.simonis.platform.testDoubles.newMenu
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GroupRepositoryImplTest {

    lateinit var groupApi: GroupApi
    lateinit var groupRepository: GroupRepository

    @Test
    fun testApi() {
        val groupResponse = createGroupResponse(
            withId = 12,
            withName = "Menu1",
            withParent = "1",
            withIsLastChild = false,
            withChildren = emptyList()
        )

        val expectedResult = newMenu(
            withId = 12L,
            withDisplayName = "Menu1",
            withParent = "1",
            withIsLastChild = false,
            withChildren = emptyList()
        )

        groupApi = mock {
            onBlocking { getGroupTree() } doReturn groupResponse
        }
        groupRepository = GroupRepositoryImpl(groupApi)

        val result = runBlocking { groupRepository.getGroupTree() }

        assertEquals(expectedResult, result)
    }
}