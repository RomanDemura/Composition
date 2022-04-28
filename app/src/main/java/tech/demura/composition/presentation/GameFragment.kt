package tech.demura.composition.presentation

import android.content.res.ColorStateList
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.demura.composition.R
import tech.demura.composition.databinding.FragmentGameBinding
import tech.demura.composition.domain.entity.GameResult
import tech.demura.composition.domain.entity.GameSettings
import tech.demura.composition.domain.entity.Level

class GameFragment : Fragment() {

    private val viewModelGameFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }

    private val viewModelGameFragment by lazy {
        ViewModelProvider(this, viewModelGameFactory)[ViewModelGameFragment::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(option1)
                add(option2)
                add(option3)
                add(option4)
                add(option5)
                add(option6)
            }
        }
    }

    private var _bindind: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _bindind ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level

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
        setOnClickListeners()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }

    private fun setOnClickListeners() {
        for (option in tvOptions) {
            option.setOnClickListener { viewModelGameFragment.chooseAnswer(option.text.toString()) }
        }
    }

    private fun setObservers() {
        //Options
        viewModelGameFragment.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for ((index, option) in tvOptions.withIndex()) {
                option.text = it.options[index].toString()
            }
        }
        //Timer
        viewModelGameFragment.formattedTimer.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        //Progress answers
        viewModelGameFragment.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        viewModelGameFragment.enoughtCountOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }
        //Progress Bar
        viewModelGameFragment.minPercentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModelGameFragment.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModelGameFragment.enoughtPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        //Finished
        viewModelGameFragment.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun getColorByState(state: Boolean): Int {
        val resColor = if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), resColor)
    }

    private fun parseArguments() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        val bundle = Bundle().apply {
            putParcelable(GameFinishedFragment.KEY_GAME_RESULT, gameResult)
        }
        findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment, bundle)
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.main_container, GameFinishedFragment.newInstance(
//                    gameResult
//                )
//            )
//            .addToBackStack(GameFinishedFragment.GAME_FINISHED_FRAGMENT)
//            .commit()
    }

    companion object {
        const val GAME_FRAGMENT = "game_fragment"
        val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

}