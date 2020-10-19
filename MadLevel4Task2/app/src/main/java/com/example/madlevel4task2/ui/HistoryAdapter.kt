package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.Winner
import kotlinx.android.synthetic.main.fragment_game_history.view.*
import kotlinx.android.synthetic.main.fragment_play.view.ivComputer
import kotlinx.android.synthetic.main.fragment_play.view.ivYou
import kotlinx.android.synthetic.main.fragment_game_history.view.tvResult

class HistoryAdapter(private val games: List<Game>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var playFragment = PlayFragment()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            itemView.ivComputer.setImageResource(playFragment.getImageResource(game.computer))
            itemView.ivYou.setImageResource(playFragment.getImageResource(game.player))
            itemView.tvDateTime.text = game.date
            itemView.tvResult.text = Winner.values()[game.winner].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_game_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }
}