package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.database.GameDatabase
import com.example.madlevel4task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun getAllPlayerWins(): Int {
        return gameDao.getAllPlayerWins()
    }

    suspend fun getAllDraws(): Int {
        return gameDao.getAllDraws()
    }

    suspend fun getAllComputerWins(): Int {
        return gameDao.getAllComputerWins()
    }

    suspend fun insertGame(game: Game) {
        return gameDao.insertGame(game)
    }

    suspend fun clearGameHistory() {
        return gameDao.clearGameHistory()
    }
}