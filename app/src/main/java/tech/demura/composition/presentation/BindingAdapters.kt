package tech.demura.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import tech.demura.composition.R
import tech.demura.composition.domain.entity.GameResult


interface OnOptionClickListener {
    fun onOptionClick(option: String)
}

@BindingAdapter("requiredAnswers")
fun requiredAnswers(textView: TextView, minCount: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_answers),
        minCount
    )
}

@BindingAdapter("scoreAnswers")
fun scoreAnswers(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentage")
fun requiredPercentage(textView: TextView, minPercent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        minPercent
    )
}

@BindingAdapter("scorePercentage")
fun scorePercentage(textView: TextView, gameResult: GameResult) {
    val percent = if (gameResult.countOfQuestions > 0)
        gameResult.countOfRightAnswers * 100 / gameResult.countOfQuestions
    else
        0
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        percent
    )
}

@BindingAdapter("emojiResult")
fun emojiResult(imageView: ImageView, winner: Boolean) {
    val emojiId = if (winner)
        R.drawable.ic_emoji_win
    else
        R.drawable.ic_emoji_lose
    imageView.setImageResource(emojiId)
}

@BindingAdapter("numberAsText")
fun sum(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("answersProgress")
fun answerProgress(textView: TextView, viewModel: ViewModelGameFragment) {
    textView.text = String.format(
        textView.context.getString(R.string.answers_progress),
        viewModel.progressAnswers.value,
        viewModel.gameSettings.minCountOfRightAnswers
    )
}

@BindingAdapter("enoughProgress")
fun enoughProgress(textView: TextView, enough: Boolean) {
    val color = getColorByState(textView.context, enough)
    textView.setTextColor(color)
}


@BindingAdapter("setProgress")
fun setProgress(progressBar: ProgressBar, progress: Int) {
    progressBar.setProgress(progress, true)
}

@BindingAdapter("enoughProgress")
fun enoughProgress(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("optionClickListener")
fun bindOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString())
    }
}

private fun getColorByState(context: Context, state: Boolean): Int {
    val resColor = if (state) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, resColor)
}