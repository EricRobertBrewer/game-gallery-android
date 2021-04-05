package com.ericrobertbrewer.gamegallery

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class GameItem(
        @StringRes val nameResourceId: Int,
        @DrawableRes val imageResourceId: Int,
        val clazz: Class<out Activity>
)
