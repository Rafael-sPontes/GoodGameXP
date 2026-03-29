package com.rafael.appdev.goodgamexp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.appdev.goodgamexp.model.Game
import com.rafael.appdev.goodgamexp.model.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    //Lista de jogos exposta como Flow para a Activity
    val allGames: Flow<List<Game>> = repository.getAllGames()

    fun insert(game: Game) = viewModelScope.launch {
        repository.insert(game)
    }

    fun update(game: Game) = viewModelScope.launch {
        repository.update(game)
    }

    fun delete(game: Game) = viewModelScope.launch {
        repository.delete(game)
    }
}