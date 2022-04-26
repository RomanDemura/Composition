package tech.demura.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.demura.composition.R
import tech.demura.composition.databinding.FragmentGameBinding
import tech.demura.composition.domain.entity.GameResult
import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level

class GameFragment : Fragment() {

    private var _bindind: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _bindind ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level
    private var gameSettings = GameSettings(22,2,4,5)
    private var gameResult = GameResult(true,1,2, gameSettings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindind = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.option1.setOnClickListener {
            launchGameFinishedFragment(gameResult)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }

    private fun parseArguments() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun launchGameFinishedFragment(
        gameResult: GameResult
    ) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container, GameFinishedFragment.newInstance(
                    gameResult
                )
            )
            .addToBackStack(GameFinishedFragment.GAME_FINISHED_FRAGMENT)
            .commit()
    }

    companion object {
        const val GAME_FRAGMENT = "game_fragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }

}