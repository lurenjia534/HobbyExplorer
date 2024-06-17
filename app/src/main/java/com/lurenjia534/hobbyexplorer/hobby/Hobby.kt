package com.lurenjia534.hobbyexplorer.hobby

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hobbies")
data class Hobby(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val info: String?,
    val nicheValue: Double?,
    val nicheInfo: String?,
    val contactTime: String?,
    val costCount: String?,
    val costCountVal: Double?,
    val timeOfDay: String?,
    val levelVal: Double?,
    val level: String?,
    val putIntoCostLevel: String?,
    val putIntoTimeLevel: String?,
    val cognitionCill: String?,
    val cognitionCillVal: Double?,
    val status: Int?
)
