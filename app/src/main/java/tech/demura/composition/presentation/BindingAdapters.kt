package tech.demura.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import tech.demura.composition.R
import tech.demura.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun requiredAnswers(textView: TextView, minCount: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_answers),
        minCount
    )
}

@BindingAdapter("scoreAnswers")
fun scoreAnswers(textView: TextView, score: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentage")
fun requiredPercentage(textView: TextView, minPercent: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        minPercent
    )
}

@BindingAdapter("scorePercentage")
fun scorePercentage(textView: TextView, gameResult: GameResult){
    val percent = if(gameResult.countOfQuestions > 0 )
        gameResult.countOfRightAnswers * 100 / gameResult.countOfQuestions
    else
        0
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        percent
    )
}

@BindingAdapter("emojiResult")
fun emojiResult(imageView: ImageView, winner: Boolean){
    val emojiId = if (winner)
        R.drawable.ic_emoji_win
    else
        R.drawable.ic_emoji_lose
    imageView.setImageResource(emojiId)
}