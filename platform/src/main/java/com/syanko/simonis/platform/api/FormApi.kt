package com.syanko.simonis.platform.api

import com.syanko.simonis.platform.response.FormResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FormApi {

    @GET("api/formByInspectionId/{id}")
    suspend fun getFormByInspection(@Path("id") id: Int): FormResponse
}