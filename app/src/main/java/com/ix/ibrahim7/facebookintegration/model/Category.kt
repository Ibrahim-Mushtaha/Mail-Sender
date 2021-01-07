package com.ix.ibrahim7.facebookintegration.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Category_table")
data class Category(
    @PrimaryKey
    val id:String="",
    val name:String="",
    val color: String=""
):Parcelable