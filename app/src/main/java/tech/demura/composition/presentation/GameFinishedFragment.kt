package tech.demura.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.demura.composition.R
import tech.demura.composition.databinding.FragmentGameFinishedBinding
import tech.demura.composition.domain.entity.GameResult
import kotlin.properties.Delegates

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private val viewModelGameFinished by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(
                requireActivity().application
            )
        )[ViewModelGameFinished::class.java]
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parseArguments()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelGameFinished.setParams(args.gameResult)
        setRetryLinsteners()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArguments() {
//        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
//            gameResult = it
//        }
//    }

    private fun setRetryLinsteners(){
        binding.buttonRetry.setOnClickListener { retryGame() }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    private fun setObservers(){
        viewModelGameFinished.winnerEmoji.observe(viewLifecycleOwner){
            binding.emojiResult.setImageResource(it)
        }
        viewModelGameFinished.countOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvScoreAnswers.text = it
        }
        viewModelGameFinished.requiredCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvRequiredAnswers.text = it
        }
        viewModelGameFinished.percentageOfRightAnswers.observe(viewLifecycleOwner){
            binding.tvScorePercentage.text = it
        }
        viewModelGameFinished.requiredPercentageOpRightAnswers.observe(viewLifecycleOwner){
            binding.tvRequiredPercentage.text = it
        }
    }

//    companion object {
//        const val GAME_FINISHED_FRAGMENT = "game_finished_fragment"
//        const val KEY_GAME_RESULT = "game_result"
//
//        fun newInstance(gameResult: GameResult): GameFinishedFragment {
//            return GameFinishedFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(KEY_GAME_RESULT, gameResult)
//                }
//            }
//        }
//    }

}