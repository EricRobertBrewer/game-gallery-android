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
import com.ericrobertbrewer.gamegallery.databinding.GamesActivityBinding
import com.ericrobertbrewer.gamegallery.gofish.GoFishActivity
import com.ericrobertbrewer.gamegallery.yacht.YachtActivity

class GamesActivity : AppCompatActivity() {

  private lateinit var binding: GamesActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = GamesActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.gamesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.gamesRecyclerView.adapter = GamesAdapter()
  }

  private class GamesAdapter : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
      return GameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
      val gameItem = GAME_ITEMS[position]
      holder.titleLabel.setText(gameItem.nameResourceId)
      holder.imageView.setImageResource(gameItem.imageResourceId)
      holder.itemView.setOnClickListener {
        val intent = Intent(it.context, gameItem.clazz)
        it.context.startActivity(intent)
      }
    }

    override fun getItemCount(): Int {
      return GAME_ITEMS.size
    }
  }

  private class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView = itemView.findViewById<AppCompatImageView>(R.id.gameItemImageView)!!
    val titleLabel = itemView.findViewById<TextView>(R.id.gameItemTitleLabel)!!
  }

  companion object {

    private val GAME_ITEMS: Array<GameItem> = arrayOf(
//                GameItem(R.string.title_checkers, R.mipmap.ic_launcher, CheckersActivity::class.java),
        GameItem(R.string.title_go_fish, R.mipmap.ic_launcher, GoFishActivity::class.java),
//                GameItem(R.string.title_dominoes, R.mipmap.ic_launcher, DominoesActivity::class.java),
//                GameItem(R.string.title_backgammon, R.mipmap.ic_launcher, BackgammonActivity::class.java),
        GameItem(R.string.title_yacht, R.mipmap.ic_launcher, YachtActivity::class.java)
    )
  }
}
