package com.example.vidgi

import androidx.room.Room

object DatabaseMananger {
    private var dbInstance: VidgiDatabase

    init {
        val appContext = VidgiApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,
            VidgiDatabase::class.java,
            "vidgi.sqlite"
        ).build()
    }

    fun getJogoDAO(): JogoDAO {
        return dbInstance.jogoDAO()
    }
}