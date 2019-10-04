package com.syanko.simonis.platform.testDoubles

import com.syanko.simonis.domain.entity.Menu

fun newMenu(
    withId: Long = 123L,
    withDisplayName: String = "Menu",
    withParent: String = "1",
    withIsLastChild: Boolean = false,
    withChildren: List<Menu> = emptyList()
): Menu {
    return Menu(
        id = withId,
        name = withDisplayName,
        parent = withParent,
        isLastChild = withIsLastChild,
        children = withChildren
    )
}
