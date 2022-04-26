package tech.demura.composition.domain.repository

import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level
import tech.demura.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOption: Int
    ): Question

    fun getGetGameSettings(level: Level): GameSettings
}