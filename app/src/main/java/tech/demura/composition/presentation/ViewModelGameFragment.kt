package tech.demura.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.demura.composition.R
import tech.demura.composition.data.GameRepositoryImpl
import tech.demura.composition.domain.entity.GameResult
import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level
import tech.demura.composition.domain.entity.Question
import tech.demura.composition.domain.usecases.GenerateQuestionUseCase
import tech.demura.composition.domain.usecases.GetGameSettingsUseCase

class ViewModelGameFragment(application: Application, private val level: Level) : ViewModel() {

    private val repository = GameRepositoryImpl
    lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private val context = application
    private var countOfAnswers = 0
    private var countOfRightAnswers = 0

    private var _formattedTimer = MutableLiveData<String>()
    val formattedTimer: LiveData<String>
        get() = _formattedTimer

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private var _enoughtCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughtCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughtCountOfRightAnswers

    private var _enoughtPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughtPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughtPercentOfRightAnswers

    private var _minPercentOfRightAnswers = MutableLiveData<Int>()
    val minPercentOfRightAnswers: LiveData<Int>
        get() = _minPercentOfRightAnswers

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }

    private fun startGame() {
        gameSettings = getGameSettingsUseCase(level)
        _minPercentOfRightAnswers.value = gameSettings.minPercentOfRightAnswers
        generateQuestion()
        updateProgress()
        startTimer()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer() {
        timer = object :
            CountDownTimer(gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND, MILLIS_IN_SECOND) {
            override fun onTick(p0: Long) {
                _formattedTimer.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(p0: Long): String {
        val seconds = p0 / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughtCountOfRightAnswers.value == true && enoughtPercentOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfAnswers,
            gameSettings = gameSettings
        )
    }

    fun chooseAnswer(answerStr: String) {
        val answer = answerStr.toInt()
        val rightAnswer = question.value?.rightAnswer
        if (answer == rightAnswer)
            countOfRightAnswers++
        countOfAnswers++
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        _percentOfRightAnswers.value = calculatePercentOfRightAnswers()
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.answers_progress),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughtCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        percentOfRightAnswers.value?.let {
            _enoughtPercentOfRightAnswers.value = it >= gameSettings.minPercentOfRightAnswers
        }
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return if (countOfAnswers == 0)
            0
        else
            countOfRightAnswers * 100 / countOfAnswers
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60L
    }

}