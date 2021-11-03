package com.ericrobertbrewer.gamegallery.util

import java.util.Random

class Die constructor(private val sides: Int) {

  private val random = Random()
  var value: Int = -1
    private set

  init {
    roll()
  }

  fun roll() {
    value = random.nextInt(sides) + 1
  }
}
