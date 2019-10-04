package com.syanko.simonis.platform.api

import com.syanko.simonis.platform.response.GroupResponse
import retrofit2.http.GET

interface GroupApi {
    @GET("/api/group")
    suspend fun getGroupTree(): List<GroupResponse>
}

