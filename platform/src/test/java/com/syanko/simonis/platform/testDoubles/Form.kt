package com.syanko.simonis.platform.testDoubles

import com.syanko.simonis.domain.entity.FieldOption
import com.syanko.simonis.domain.entity.Form
import com.syanko.simonis.domain.entity.FormField
import com.syanko.simonis.platform.response.FieldOptionResponse
import com.syanko.simonis.platform.response.FormFieldResponse
import com.syanko.simonis.platform.response.FormResponse

fun createForm(
    withId: Int = -1,
    withTitle: String = "title",
    withDescription: String = "description",
    withFields: List<FormField> = emptyList()
): Form {
    return Form(
        id = withId,
        title = withTitle,
        description = withDescription,
        fields = withFields
    )
}

fun createFormField(
    withId: Int = -1,
    withLabel: String = "label",
    withType: Int = 1,
    withOrder: Int = 0,
    withDescription: String = "description",
    withHint: String = "hint",
    withOptions: List<FieldOption> = emptyList()
): FormField {
    return FormField(
        id = withId,
        label = withLabel,
        type = withType,
        order = withOrder,
        description = withDescription,
        hint = withHint,
        options = withOptions
    )
}

fun createFieldOption(
    withId: Int = 1,
    withValue: String = "value"
): FieldOption {
    return FieldOption(
        id = withId,
        value = withValue
    )
}

fun newFormResponse(): FormResponse {
    return FormResponse(listOf(newForm()))
}

fun newForm(
    withId: Int = 1,
    withTitle: String = "title",
    withDescription: String = "description",
    withFields: List<FormFieldResponse> = emptyList()
): com.syanko.simonis.platform.response.Form {
    return com.syanko.simonis.platform.response.Form(withId, withTitle, withDescription, withFields)
}

fun newFormFieldResponse(
    withId: Int = 1,
    withFormId: Int = 1,
    withLabel: String = "label",
    withType: Int = 1,
    withOrder: Int = 1,
    withDescription: String = "description",
    withHint: String = "hint",
    withOptions: List<FieldOptionResponse> = emptyList()
): FormFieldResponse {
    return FormFieldResponse(
        withId,
        withFormId,
        withLabel,
        withType,
        withOrder,
        withDescription,
        withHint,
        withOptions
    )
}