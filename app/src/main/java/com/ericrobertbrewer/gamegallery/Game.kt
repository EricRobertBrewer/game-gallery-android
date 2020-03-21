package com.ericrobertbrewer.gamegallery

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Game(
        @StringRes val nameResourceId: Int,
        @DrawableRes val imageResourceId: Int,
        val aClass: Class<out Activity>
)
