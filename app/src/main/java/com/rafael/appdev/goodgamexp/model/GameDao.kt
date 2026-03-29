package com.rafael.appdev.goodgamexp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    // 1. Inserir um novo jogo
    @Insert
    suspend fun insert(game: Game)

    // 2. Atualizar um jogo existente
    @Update
    suspend fun update(game: Game)

    // 3. Deletar um jogo
    @Delete
    suspend fun delete(game: Game)

    // 4. Deletar um jogo pelo ID (opcional, mas útil)
    @Query("DELETE FROM games_table WHERE id = :gameId")
    suspend fun deleteById(gameId: Int)

    // 5. Obter todos os jogos. Usar 'Flow' para atualizações em tempo real
    @Query("SELECT * FROM games_table ORDER BY title ASC")
    fun getAllGames(): Flow<List<Game>>

    // 6. Obter um jogo pela position ID:
    /*@Query("SELECT * FROM games_table WHERE id = :gameId")
    suspend fun getGameById(gameId: Int): Game?*/
}