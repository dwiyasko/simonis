package com.syanko.simonis.platform.testDoubles

import com.syanko.simonis.platform.response.GroupResponse

fun createGroupResponse(
    withId: Int = 123,
    withName: String = "name",
    withParent: String = "1",
    withIsLastChild: Boolean = false,
    withChildren: List<GroupResponse> = emptyList()
): GroupResponse {
    return GroupResponse(
        id = withId,
        parent = withParent,
        name = withName,
        isLastChild = withIsLastChild,
        children = withChildren
    )
}
