package com.ix.ibrahim7.mailsender.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Message_table")
data class Message(
    @PrimaryKey
    val id:String="",
    val To:String="",
    val note:String="",
    val date: String=""
): Parcelable