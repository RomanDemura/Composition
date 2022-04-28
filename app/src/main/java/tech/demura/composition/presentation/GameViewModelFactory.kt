package tech.demura.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.demura.composition.domain.entity.Level


class GameViewModelFactory(private val application: Application, private val level: Level): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if ( modelClass.isAssignableFrom(ViewModelGameFragment::class.java))
            return ViewModelGameFragment(application, level) as T
        else
            throw RuntimeException("Uknown viewModel: $modelClass")
    }
}