package com.example.madlevel4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "player")
    var player: Int,

    @ColumnInfo(name = "computer")
    var computer: Int,

    @ColumnInfo(name = "winner")
    var winner: Int,

    @ColumnInfo(name = "date")
    var date: String
)