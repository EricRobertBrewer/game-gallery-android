package com.ericrobertbrewer.gamegallery.yacht.ui

import androidx.lifecycle.ViewModel
import com.ericrobertbrewer.gamegallery.util.Die

class YachtViewModel : ViewModel() {

    companion object {
        const val LINE_ONES = 0
        const val LINE_TWOS = 1
        const val LINE_THREES = 2
        const val LINE_FOURS = 3
        const val LINE_FIVES = 4
        const val LINE_SIXES = 5
        const val LINE_THREE_OF_A_KIND = 6
        const val LINE_FOUR_OF_A_KIND = 7
        const val LINE_FULL_HOUSE = 8
        const val LINE_SMALL_STRAIGHT = 9
        const val LINE_LARGE_STRAIGHT = 10
        const val LINE_YACHT = 11
        const val LINE_CHOICE = 12
        const val NUM_LINES = 13

        const val NUM_DICE = 5
        const val NUM_ROLLS = 3
    }

    val scores = IntArray(NUM_LINES) { 0 }
    val isScoreMarked = BooleanArray(NUM_LINES) { false }
    val upperTotal
        get() = scores.filterIndexed { line, _ -> line <= LINE_SIXES }.sum()
    val upperBonus
        get() = if (upperTotal >= 63) 35 else 0
    val lowerTotal
        get() = scores.filterIndexed { line, _ -> line >= LINE_THREE_OF_A_KIND }.sum()
    val grandTotal
        get() = upperTotal + upperBonus + lowerTotal

    val dice = Array(NUM_DICE) { Die(6) }
    val isDieHeld = BooleanArray(NUM_DICE) { false }
    var rolls = 0
        private set

    init {
        newGame()
    }

    fun newGame() {
        scores.fill(0)
        isScoreMarked.fill(false)
        startNextRound()
    }

    private fun startNextRound() {
        isDieHeld.fill(false)
        rolls = NUM_ROLLS
        rollDice()
    }

    fun canRollDice() = canMark() && rolls > 0

    fun rollDice() {
        dice.forEachIndexed { i, die -> if (!isDieHeld[i]) die.roll() }
        rolls -= 1
    }

    fun canMark() = isScoreMarked.any { m -> !m }

    fun mark(line: Int) {
        if (line < 0 || line >= NUM_LINES) return
        if (isScoreMarked[line]) return
        scores[line] = score(line)
        isScoreMarked[line] = true
        if (canMark()) startNextRound()
    }

    fun score(line: Int): Int {
        when (line) {
            LINE_ONES,
            LINE_TWOS,
            LINE_THREES,
            LINE_FOURS,
            LINE_FIVES,
            LINE_SIXES -> return values(dice).filter { value -> value == line + 1 }.sum()
            LINE_THREE_OF_A_KIND -> return if (hasLikeDice(dice, 3)) sum(dice) else 0
            LINE_FOUR_OF_A_KIND -> return if (hasLikeDice(dice, 4)) sum(dice) else 0
            LINE_FULL_HOUSE -> return if (hasFullHouse(dice)) 25 else 0
            LINE_SMALL_STRAIGHT -> return if (hasStraight(dice, 4)) 30 else 0
            LINE_LARGE_STRAIGHT -> return if (hasStraight(dice, 5)) 40 else 0
            LINE_YACHT -> return if (values(dice).all { value -> value == dice[0].value }) 50 else 0
            LINE_CHOICE -> return sum(dice)
        }
        throw IllegalArgumentException()
    }

    private fun values(dice: Array<Die>) = dice.map { die -> die.value }

    private fun sum(dice: Array<Die>) = values(dice).sum()

    private fun counts(dice: Array<Die>): Map<Int, Int> {
        val counts = HashMap<Int, Int>()
        values(dice).forEach { value ->
            val count = counts[value]
            counts[value] = if (count != null) count + 1 else 1
        }
        return counts
    }

    private fun hasLikeDice(dice: Array<Die>, count: Int) = counts(dice).values.any { value -> value >= count }

    private fun hasFullHouse(dice: Array<Die>): Boolean {
        val counts = counts(dice)
        if (counts.size != 2) return false
        val keys = counts.keys.toList()
        return (counts[keys[0]] == 2 && counts[keys[1]] == 3) ||
                (counts[keys[0]] == 3 && counts[keys[1]] == 2)
    }

    private fun hasStraight(dice: Array<Die>, count: Int): Boolean {
        if (count < 0) return false
        if (count == 0) return true
        if (count == 1) return dice.isNotEmpty()
        val orderedValues = values(dice).toSet().toList().sorted()
        if (orderedValues.size < count) return false
        for (i in 0..orderedValues.size - count) {
            val slice = orderedValues.slice(i + 1 until i + count)
            if (slice.withIndex().all { (j, value) -> value == orderedValues[i + j] + 1 }) return true
        }
        return false
    }
}
