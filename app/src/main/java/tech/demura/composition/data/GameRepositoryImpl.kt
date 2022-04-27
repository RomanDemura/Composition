package tech.demura.composition.data

import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level
import tech.demura.composition.domain.entity.Question
import tech.demura.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 15
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question {
        val sumValue = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleValue = Random.nextInt(MIN_ANSWER_VALUE, sumValue)
        val rightAnswer = sumValue - visibleValue
        val options = HashSet<Int>()
        options.add(rightAnswer)
        val from = max(MIN_ANSWER_VALUE, rightAnswer - countOfOption)
        val to = min(sumValue, rightAnswer + countOfOption)
        while (options.size < countOfOption) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sumValue, visibleValue, options = options.toList())
    }

    override fun getGetGameSettings(level: Level): GameSettings {
         return when (level) {
            Level.TEST -> GameSettings(
                25,
                2,
                10,
                8
            )
            Level.EASY -> GameSettings(
                25,
                5,
                30,
                60
            )
            Level.MEDIUM -> GameSettings(
                100,
                10,
                50,
                60
            )
            Level.HARD -> GameSettings(
                1000,
                20,
                80,
                90
            )
        }
    }
}