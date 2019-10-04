package com.syanko.simonis.domain.entity

data class Form(
    val id: Int,
    val title: String,
    val description: String,
    val periodId: Int,
    val periodName: String,
    val substationId: Int,
    val substationName: String,
    val voltageId: Int,
    val voltageName: String,
    val bayId: Int,
    val bayName: String,
    val sections: List<Section>
)

data class Section(
    val id: Int,
    val formId: Int,
    val parent: Int,
    val title: String,
    val fields: List<FormField>,
    val children: List<Section>
)

data class FormField(
    val id: Int,
    val label: String,
    val type: Int,
    val order: Int,
    val description: String,
    val fieldSections: List<FormFieldSection>
)

data class FormFieldSection(
    val id: Int,
    val formFieldId: Int,
    val label: String,
    val type: FormType,
    val options: List<FieldOption>
)

data class FieldOption(
    val id: Int,
    val value: String,
    val formFieldSectionId: Int
)

enum class FormType(val code: Int) {
    TEXT(1),
    NUMBER(4),
    DATE(5),
    RADIO(2),
    CHECKBOX(3),
    UNKNOWN(-1)
}

