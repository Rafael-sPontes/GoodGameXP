package com.rafael.appdev.goodgamexp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafael.appdev.goodgamexp.databinding.AdapterGamesBinding
import com.rafael.appdev.goodgamexp.model.Game

//Adiciona o parâmetro lambda ao construtor: onEditClick
class GameAdapter(
    var games: MutableList<Game>,
    private val onEditClick: (Game, Int) -> Unit /*Callback que retorna o objeto Game clicado*/
) : RecyclerView.Adapter<GameAdapter.GameHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameHolder {
        AdapterGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return GameHolder(this)
        }
    }

    override fun onBindViewHolder(
        holder: GameHolder,
        position: Int //Posição do item na lista
    ) {
        games[position].also { game ->
            holder.binding.txtTitle.text = game.title
            holder.binding.txtPlatform.text = game.platform
            holder.binding.gameProgress.progress = game.progress
            holder.binding.txtProgress.text = game.txtProgress
            holder.binding.txtRate.text = game.txtRate
            holder.binding.imgCover.setImageResource(game.imgCover)
            holder.binding.imgPlatform.setImageResource(game.imgPlatform)

            //Button btn_edit
            holder.binding.btnEdit.setOnClickListener {
                onEditClick(game, position)
            }
        }
    }

    override fun getItemCount() = games.size

    inner class GameHolder(var binding: AdapterGamesBinding) : RecyclerView.ViewHolder(binding.root)
}