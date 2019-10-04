package com.syanko.simonis.platform.response

import com.google.gson.annotations.SerializedName

data class FormResponse(
    val form: List<Form>
)

data class Form(
    val id: Int,
    val title: String,
    val description: String,

    @SerializedName("period_id")
    val periodId: Int,
    @SerializedName("period_name")
    val periodName: String,
    @SerializedName("gardu_induk_id")
    val substationId: Int,
    @SerializedName("gardu_induk")
    val substationName: String,
    @SerializedName("tegangan_id")
    val voltageId: Int,
    @SerializedName("tegangan")
    val voltageName: String,
    @SerializedName("bay_id")
    val bayId: Int,
    @SerializedName("bay")
    val bayName: String,

    val sections: List<SectionResponse>
)

data class SectionResponse(
    val id: Int,

    @SerializedName("form_id")
    val formId: Int,
    val parent: Int,
    val title: String,
    val fields: List<FormFieldResponse>,
    val children: List<SectionResponse>
)

data class FormFieldResponse(
    val id: Int,

    @SerializedName("formsection_id")
    val formSectionId: Int,
    val label: String,
    val type: Int,
    val order: Int,
    val description: String,

    @SerializedName("fieldsections")
    val fieldSections: List<FieldSectionResponse>
)

data class FieldSectionResponse(
    val id: Int,
    @SerializedName("formfield_id")
    val formFieldId: Int,
    val label: String,
    val type: Int,
    val options: List<FieldOptionResponse>
)

data class FieldOptionResponse(
    val id: Int,

    @SerializedName("formfieldsection_id")
    val formFieldSectionId: Int,
    val value: String
)
