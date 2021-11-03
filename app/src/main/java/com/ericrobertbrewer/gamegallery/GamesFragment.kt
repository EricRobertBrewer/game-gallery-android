package com.ericrobertbrewer.gamegallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ericrobertbrewer.gamegallery.databinding.GamesFragmentBinding

class GamesFragment : Fragment() {

  private var _binding: GamesFragmentBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = GamesFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setHasOptionsMenu(true)
    binding.gamesRecyclerView.layoutManager =
      LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    binding.gamesRecyclerView.adapter = GamesAdapter(this)
  }

  private class GamesAdapter(var fragment: Fragment) : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
      return GameViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
      )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
      val gameItem = GAME_ITEMS[position]
      holder.titleLabel.setText(gameItem.nameResourceId)
      holder.imageView.setImageResource(gameItem.imageResourceId)
      holder.itemView.setOnClickListener {
        NavHostFragment.findNavController(fragment).navigate(gameItem.actionResourceId)
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
      GameItem(
        R.string.title_go_fish,
        R.mipmap.ic_launcher,
        R.id.action_GamesFragment_to_GoFishFragment
      ),
//                GameItem(R.string.title_dominoes, R.mipmap.ic_launcher, DominoesActivity::class.java),
//                GameItem(R.string.title_backgammon, R.mipmap.ic_launcher, BackgammonActivity::class.java),
      GameItem(
        R.string.title_yacht,
        R.mipmap.ic_launcher,
        R.id.action_GamesFragment_to_YachtFragment
      )
    )
  }
}