package com.ix.ibrahim7.facebookintegration.model.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Users

data class CategoryAndUsers(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryID"
    )
    val users: List<Users>
)