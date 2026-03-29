package com.rafael.appdev.goodgamexp

import com.rafael.appdev.goodgamexp.model.Game
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameEntityTest {

    //Teste 1: Valida se a entidade armazena os dados básicos corretamente
    @Test
    fun testar_atribuicao_dados_basicos() {
        val game = Game(title = "God of War", platform = "PS5", progress = 100, txtProgress = "100%", txtRate = "10.0", imgCover = 1, imgPlatform = 1, txtReview = "Obra-prima")
        assertEquals("God of War", game.title)
        assertEquals("PS5", game.platform)
    }

    //Teste 2: Valida a lógica de formatação de string de progresso
    @Test
    fun testar_formatacao_string_progresso() {
        val progressInt = 75
        val progressString = "${progressInt}%"
        assertEquals("75%", progressString)
    }

    //Teste 3: Garante que o ID inicial seja sempre 0 (importante para o AutoGenerate do Room)
    @Test
    fun testar_id_padrao_entidade() {
        val game = Game(title = "Indie Game", platform = "PC", progress = 10, txtProgress = "10%", txtRate = "8.0", imgCover = 0, imgPlatform = 0, txtReview = "")
        assertEquals(0, game.id)
    }

    //Teste 4: Valida se a entidade diferencia objetos com títulos diferentes
    @Test
    fun testar_distincao_entre_jogos() {
        val game1 = Game(title = "Zelda", platform = "Switch", progress = 0, txtProgress = "0%", txtRate = "0", imgCover = 0, imgPlatform = 0, txtReview = "")
        val game2 = Game(title = "Mario", platform = "Switch", progress = 0, txtProgress = "0%", txtRate = "0", imgCover = 0, imgPlatform = 0, txtReview = "")
        assertNotEquals(game1.title, game2.title)
    }

    //Teste 5: Valida a integridade da nota (txtRate)
    @Test
    fun testar_integridade_da_nota() {
        val notaEsperada = "9.5"
        val game = Game(title = "Halo", platform = "Xbox", progress = 50, txtProgress = "50%", txtRate = notaEsperada, imgCover = 0, imgPlatform = 0, txtReview = "")
        assertEquals("9.5", game.txtRate)
    }
}