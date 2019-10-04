package com.syanko.simonis.platform.api

import com.syanko.simonis.platform.response.InspectionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface InspectionApi {

    @GET("/api/inspectionschedule/{id}")
    suspend fun getInspectionByEquipment(@Path("id") id: Int): InspectionResponse
}
