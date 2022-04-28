package tech.demura.composition.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.demura.composition.R
import tech.demura.composition.domain.entity.GameResult
import tech.demura.composition.domain.entity.GameSettings

class ViewModelGameFinished(application: Application) : AndroidViewModel(application) {

    private val context = application
    private lateinit var gameSettings: GameSettings
    private lateinit var gameResult: GameResult
    private var winner = false

    private val _winnerEmoji = MutableLiveData<Int>()
    val winnerEmoji: LiveData<Int>
        get() = _winnerEmoji

    private val _countOfRightAnswers = MutableLiveData<String>()
    val countOfRightAnswers: LiveData<String>
        get() = _countOfRightAnswers

    private val _requiredCountOfRightAnswers = MutableLiveData<String>()
    val requiredCountOfRightAnswers: LiveData<String>
        get() = _requiredCountOfRightAnswers

    private val _percentageOfRightAnswers = MutableLiveData<String>()
    val percentageOfRightAnswers: LiveData<String>
        get() = _percentageOfRightAnswers

    private val _requiredPercentageOpRightAnswers = MutableLiveData<String>()
    val requiredPercentageOpRightAnswers: LiveData<String>
        get() = _requiredPercentageOpRightAnswers

    fun setParams(gameResult: GameResult) {
        this.gameResult = gameResult
        gameSettings = gameResult.gameSettings
        setLiveDatas()
    }

    private fun getEmoji(): Int {
        return if (gameResult.winner)
            R.drawable.ic_emoji_win
        else
            R.drawable.ic_emoji_lose
    }

    private fun setLiveDatas() {
        _winnerEmoji.value = getEmoji()

        val strCountOfRightAnswers = String.format(
            context.resources.getString(R.string.score_answers),
            gameResult.countOfRightAnswers
        )
        _countOfRightAnswers.value = strCountOfRightAnswers

        val strRequiredCountOfRightAnswers = String.format(
            context.resources.getString(R.string.required_answers),
            gameSettings.minCountOfRightAnswers
        )
        _requiredCountOfRightAnswers.value = strRequiredCountOfRightAnswers

        val percentOfRightAnswers = if (gameResult.countOfQuestions != 0)
            gameResult.countOfRightAnswers * 100 / gameResult.countOfQuestions
        else
            0

        val strPercentageOfRightAnswers = String.format(
            context.resources.getString(R.string.score_percentage),
            percentOfRightAnswers
        )
        _percentageOfRightAnswers.value = strPercentageOfRightAnswers

        val strRequiredPercentageOpRightAnswers = String.format(
            context.resources.getString(R.string.required_percentage),
            gameSettings.minPercentOfRightAnswers
        )
        _requiredPercentageOpRightAnswers.value = strRequiredPercentageOpRightAnswers
    }
}