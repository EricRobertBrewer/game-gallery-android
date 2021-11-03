package com.ericrobertbrewer.gamegallery

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

data class GameItem(
  @StringRes val nameResourceId: Int,
  @DrawableRes val imageResourceId: Int,
  @IdRes val actionResourceId: Int
)
