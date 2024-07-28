package com.atta.notestakingapp.Notes

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Long =0,
    var title:String="",
    var description:String="",
    var date:String=""
)