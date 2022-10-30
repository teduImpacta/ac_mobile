package com.example.vidgi

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Jogo::class), version = 1)
abstract class VidgiDatabase : RoomDatabase() {
    abstract fun jogoDAO(): JogoDAO
}