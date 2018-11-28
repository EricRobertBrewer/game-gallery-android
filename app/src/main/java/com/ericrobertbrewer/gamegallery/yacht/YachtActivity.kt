package com.ericrobertbrewer.gamegallery.yacht

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ericrobertbrewer.gamegallery.R
import com.ericrobertbrewer.gamegallery.yacht.ui.YachtFragment

class YachtActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yacht_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, YachtFragment.newInstance())
                    .commitNow()
        }
    }
}
