package com.ericrobertbrewer.gamegallery.gofish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericrobertbrewer.gamegallery.R

class GoFishFragment : Fragment() {

  companion object {
    fun newInstance() = GoFishFragment()
  }

  private lateinit var viewModel: GoFishViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.go_fish_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProvider(this)[GoFishViewModel::class.java]
    // TODO: Use the ViewModel
  }

}
