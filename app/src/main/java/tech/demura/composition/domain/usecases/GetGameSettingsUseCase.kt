package tech.demura.composition.domain.usecases

import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level
import tech.demura.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke (level: Level): GameSettings{
        return repository.getGetGameSettings(level)
    }
}