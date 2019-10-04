package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.Menu

interface GroupRepository {
    suspend fun getGroupTree(): List<Menu>
}