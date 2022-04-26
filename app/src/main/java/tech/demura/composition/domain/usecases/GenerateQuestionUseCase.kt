package tech.demura.composition.domain.usecases

import tech.demura.composition.domain.entity.Question
import tech.demura.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }
}