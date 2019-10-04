package com.syanko.simonis.platform.repository

import com.syanko.simonis.domain.entity.Menu
import com.syanko.simonis.domain.repository.GroupRepository
import com.syanko.simonis.platform.api.GroupApi
import com.syanko.simonis.platform.response.GroupResponse

class GroupRepositoryImpl(private val groupApi: GroupApi) : GroupRepository {
    override suspend fun getGroupTree(): List<Menu> {
        return groupApi.getGroupTree().map { group ->
            group.mapToMenu()
        }
    }
}

private fun GroupResponse.mapToMenu(): Menu {
    return Menu(
        id.toLong(),
        name,
        parent,
        isLastChild,
        children.map { it.mapToMenu() }
    )
}
