package com.rafael.appdev.goodgamexp.model

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class GameRepository @Inject constructor(
    private val gameDao: GameDao //Hilt - injeta o DAO (automaticamente)
) {
    //Retorna o Flow do Room (Atualização em tempo real)
    fun getAllGames(): Flow<List<Game>> = gameDao.getAllGames()

    suspend fun insert(game: Game) = gameDao.insert(game)

    suspend fun update(game: Game) = gameDao.update(game)

    suspend fun delete(game: Game) = gameDao.delete(game)
}