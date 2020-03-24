package com.ericrobertbrewer.gamegallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ericrobertbrewer.gamegallery.yacht.YachtActivity
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        gamesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gamesRecyclerView.adapter = GamesAdapter()
    }

    private class GamesAdapter : RecyclerView.Adapter<GameViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
            return GameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false))
        }

        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            val game = games[position]
            holder.titleLabel.setText(game.nameResourceId)
            holder.imageView.setImageResource(game.imageResourceId)
            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, game.aClass)
                it.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return games.size
        }
    }

    private class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<AppCompatImageView>(R.id.gameItemImageView)!!
        val titleLabel = itemView.findViewById<TextView>(R.id.gameItemTitleLabel)!!
    }

    companion object {

        private val games: Array<Game> = arrayOf(
//                Game(R.string.title_checkers, R.mipmap.ic_launcher, CheckersActivity::class.java),
//                Game(R.string.title_go_fish, R.mipmap.ic_launcher, GoFishActivity::class.java),
//                Game(R.string.title_dominoes, R.mipmap.ic_launcher, DominoesActivity::class.java),
//                Game(R.string.title_backgammon, R.mipmap.ic_launcher, BackgammonActivity::class.java),
                Game(R.string.title_yacht, R.mipmap.ic_launcher, YachtActivity::class.java)
        )
    }
}
