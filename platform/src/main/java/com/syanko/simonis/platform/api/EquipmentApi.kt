package com.syanko.simonis.platform.api

import com.syanko.simonis.platform.response.EquipmentResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EquipmentApi {

    @GET("api/equipment/{id}")
    suspend fun getEquipmentByGroupId(@Path("id") id: Int): EquipmentResponse
}