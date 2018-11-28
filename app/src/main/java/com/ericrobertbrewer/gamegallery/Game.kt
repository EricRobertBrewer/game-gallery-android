package com.ericrobertbrewer.gamegallery

import android.app.Activity
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class Game(
        @StringRes val nameResourceId: Int,
        @DrawableRes val imageResourceId: Int,
        val aClass: Class<out Activity>
)
