package com.rafael.appdev.goodgamexp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity - mapeia a classe SQLite
@Entity(tableName = "games_table")
class Game(
    //@PrimaryKey - define o campo 'id' como chave primária (gerada automaticamente)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, //Novo - campo para o ID

    var title: String,
    var platform: String,
    var progress: Int,
    var txtProgress: String,
    var txtRate: String,
    var imgCover: Int,
    var imgPlatform: Int,
    var txtReview: String
) : Serializable {
    /*O Serializable permite o object no Intent (editMode)*/
}