package com.example.vidgi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JogoDAO {
    @Query("SELECT * FROM jogo where id = :id")
    fun getById(id: Long?): Jogo?

    @Query("SELECT * FROM jogo")
    fun findAll(): List<Jogo>

    @Insert
    fun insert(jogo: Jogo)

    @Delete
    fun delete(jogo: Jogo)
}