package com.example.vidgi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "jogo")
class Jogo : Serializable {
    @PrimaryKey
    var id:Long = 0
    var nome = ""
    var descricao = ""
    var url = ""

    override fun toString(): String {
        return "Jogo(nome='$nome')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}