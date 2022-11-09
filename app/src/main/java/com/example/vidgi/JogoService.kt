package com.example.vidgi

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response
import okhttp3.ResponseBody

object JogoService {
    val host = "https://tedu.pythonanywhere.com"

    fun getJogos(): List<Jogo> {
        var games = ArrayList<Jogo>()

        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/games"

            val json = HttpHelper.get(url)
            games = parserJson(json)

            for (game in games) {
                saveOffline(game)
            }

            return games
        } else {
            val dao = DatabaseMananger.getJogoDAO()

            return dao.findAll()
        }
    }

    fun getJogo(id: Long): Jogo? {
        var jogo = Jogo()
        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/games/${id}"
            val json = HttpHelper.get(url)

            jogo = parserJson(json)

            return jogo
        } else {
            val dao = DatabaseMananger.getJogoDAO()

            return dao.getById(id)
        }
    }

    fun saveOffline(jogo: Jogo): Boolean {
        val dao = DatabaseMananger.getJogoDAO()

        if (!alreadyGame(jogo)) {
            dao.insert(jogo)
        }
        return true
    }

    fun alreadyGame(jogo: Jogo): Boolean {
        val dao = DatabaseMananger.getJogoDAO()
        return dao.getById(jogo.id) != null
    }

    fun save(jogo: Jogo): String {
        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/games"
            val json = HttpHelper.post(url, jogo.toJson())
            return parserJson(json)
        } else {
            saveOffline(jogo)
            return "Salvo"
        }
    }

    fun delete(jogo: Jogo): String {
        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/games/${jogo.id}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        } else {
            val dao = DatabaseMananger.getJogoDAO()
            dao.delete(jogo)
            return "Deletado"
        }
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}