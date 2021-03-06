package com.ericrobertbrewer.gamegallery.yacht.ui

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ericrobertbrewer.gamegallery.R
import kotlinx.android.synthetic.main.yacht_fragment.*

class YachtFragment : Fragment() {

    companion object {
        fun newInstance() = YachtFragment()
    }

    private lateinit var viewModel: YachtViewModel

    private lateinit var scoreLayouts: Array<ConstraintLayout>
    private lateinit var scoreTitleLabels: Array<TextView>
    private lateinit var scoreScoreLabels: Array<TextView>

    private lateinit var dieButtons: Array<Button>
    private lateinit var dieHeldButtons: Array<Button>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.yacht_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(YachtViewModel::class.java)
        scoreLayouts = arrayOf(
                yachtUpperOnesLayout,
                yachtUpperTwosLayout,
                yachtUpperThreesLayout,
                yachtUpperFoursLayout,
                yachtUpperFivesLayout,
                yachtUpperSixesLayout,
                yachtLowerThreeOfAKindLayout,
                yachtLowerFourOfAKindLayout,
                yachtLowerFullHouseLayout,
                yachtLowerSmallStraightLayout,
                yachtLowerLargeStraightLayout,
                yachtLowerYachtLayout,
                yachtLowerChoiceLayout
        )
        scoreLayouts.forEachIndexed { line, layout ->
            layout.setOnClickListener {
                if (!viewModel.isScoreMarked[line]) {
                    viewModel.mark(line)
                    updateScoresAndRelated()
                    updateRollLabelAndButton()
                    updateDice()
                }
            }
        }
        scoreTitleLabels = arrayOf(
                yachtUpperOnesTitleLabel,
                yachtUpperTwosTitleLabel,
                yachtUpperThreesTitleLabel,
                yachtUpperFoursTitleLabel,
                yachtUpperFivesTitleLabel,
                yachtUpperSixesTitleLabel,
                yachtLowerThreeOfAKindTitleLabel,
                yachtLowerFourOfAKindTitleLabel,
                yachtLowerFullHouseTitleLabel,
                yachtLowerSmallStraightTitleLabel,
                yachtLowerLargeStraightTitleLabel,
                yachtLowerYachtTitleLabel,
                yachtLowerChoiceTitleLabel
        )
        scoreScoreLabels = arrayOf(
                yachtUpperOnesScoreLabel,
                yachtUpperTwosScoreLabel,
                yachtUpperThreesScoreLabel,
                yachtUpperFoursScoreLabel,
                yachtUpperFivesScoreLabel,
                yachtUpperSixesScoreLabel,
                yachtLowerThreeOfAKindScoreLabel,
                yachtLowerFourOfAKindScoreLabel,
                yachtLowerFullHouseScoreLabel,
                yachtLowerSmallStraightScoreLabel,
                yachtLowerLargeStraightScoreLabel,
                yachtLowerYachtScoreLabel,
                yachtLowerChoiceScoreLabel
        )
        yachtRollButton.setOnClickListener {
            viewModel.rollDice()
            updateScores()
            updateRollLabelAndButton()
            updateDice()
        }
        dieButtons = arrayOf(
                yachtDieButton0,
                yachtDieButton1,
                yachtDieButton2,
                yachtDieButton3,
                yachtDieButton4
        )
        dieButtons.forEachIndexed { i, button ->
            button.setOnClickListener {
                if (viewModel.canRollDice()) {
                    viewModel.isDieHeld[i] = true
                    updateDie(i)
                }
            }
        }
        dieHeldButtons = arrayOf(
                yachtDieHeldButton0,
                yachtDieHeldButton1,
                yachtDieHeldButton2,
                yachtDieHeldButton3,
                yachtDieHeldButton4
        )
        dieHeldButtons.forEachIndexed { i, button ->
            button.setOnClickListener {
                if (viewModel.canRollDice()) {
                    viewModel.isDieHeld[i] = false
                    updateDie(i)
                }
            }
        }
        updateAll()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.yacht, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.yachtMenuNewGame -> {
                viewModel.newGame()
                updateAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateAll() {
        updateScoresAndRelated()
        updateRollLabelAndButton()
        updateDice()
    }

    private fun updateScoresAndRelated() {
        updateScores()
        updateUpperTotal()
        updateUpperBonus()
        updateLowerTotal()
        updateGrandTotal()
    }

    private fun updateScores() {
        viewModel.scores.forEachIndexed { line, _ ->
            updateScore(line)
        }
    }

    private fun updateScore(line: Int) {
        if (viewModel.isScoreMarked[line]) {
            scoreTitleLabels[line].setTypeface(null, Typeface.NORMAL)
            scoreScoreLabels[line].text = viewModel.scores[line].toString()
            scoreScoreLabels[line].setTextColor(scoreTitleLabels[line].textColors)
            scoreScoreLabels[line].setTypeface(null, Typeface.NORMAL)
        } else {
            scoreTitleLabels[line].setTypeface(null, Typeface.BOLD)
            val score = viewModel.score(line)
            if (score != 0) {
                scoreScoreLabels[line].text = score.toString()
                if (Build.VERSION.SDK_INT < 23) {
                    @Suppress("DEPRECATION")
                    scoreScoreLabels[line].setTextColor(resources.getColor(R.color.colorPrimary))
                } else {
                    scoreScoreLabels[line].setTextColor(resources.getColor(R.color.colorPrimary, context?.theme))
                }
            } else {
                scoreScoreLabels[line].setText(R.string.yacht_score_not_marked)
                if (Build.VERSION.SDK_INT < 23) {
                    @Suppress("DEPRECATION")
                    scoreScoreLabels[line].setTextColor(resources.getColor(R.color.colorAccent))
                } else {
                    scoreScoreLabels[line].setTextColor(resources.getColor(R.color.colorAccent, context?.theme))
                }
            }
            scoreScoreLabels[line].setTypeface(null, Typeface.BOLD)
        }
    }

    private fun updateUpperTotal() {
        yachtUpperTotalScoreLabel.text = viewModel.upperTotal.toString()
    }

    private fun updateUpperBonus() {
        yachtUpperBonusScoreLabel.text = viewModel.upperBonus.toString()
    }

    private fun updateLowerTotal() {
        yachtLowerTotalScoreLabel.text = viewModel.lowerTotal.toString()
    }

    private fun updateGrandTotal() {
        yachtGrandTotalScoreLabel.text = viewModel.grandTotal.toString()
    }

    private fun updateRollLabelAndButton() {
        if (!viewModel.canMark()) {
            yachtRollLabel.setText(R.string.game_over)
        } else {
            when (viewModel.rolls) {
                2 -> {
                    yachtRollLabel.setText(R.string.yacht_roll_or_mark)
                    yachtRollButton.setText(R.string.yacht_roll_2)
                }
                1 -> {
                    yachtRollLabel.setText(R.string.yacht_roll_or_mark)
                    yachtRollButton.setText(R.string.yacht_roll_1)
                }
                0 -> {
                    yachtRollLabel.setText(R.string.yacht_mark)
                }
            }
        }
        yachtRollButton.isEnabled = viewModel.canRollDice()
    }

    private fun updateDice() {
        dieButtons.forEachIndexed { i, _ -> updateDie(i) }
    }

    private fun updateDie(i: Int) {
        val valueString = viewModel.dice[i].value.toString()
        dieButtons[i].text = valueString
        dieHeldButtons[i].text = valueString
        if (viewModel.isDieHeld[i]) {
            dieButtons[i].visibility = View.INVISIBLE
            dieHeldButtons[i].visibility = View.VISIBLE
        } else {
            dieButtons[i].visibility = View.VISIBLE
            dieHeldButtons[i].visibility = View.INVISIBLE
        }
    }
}
