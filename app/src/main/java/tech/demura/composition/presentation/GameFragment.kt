package tech.demura.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.demura.composition.databinding.FragmentGameBinding
import tech.demura.composition.domain.entity.GameResult

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelGameFactory by lazy {
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val viewModelGameFragment by lazy {
        ViewModelProvider(this, viewModelGameFactory)[ViewModelGameFragment::class.java]
    }

    private var _bindind: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _bindind ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindind = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModelGameFragment
        binding.lifecycleOwner = viewLifecycleOwner
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }

    private fun setObservers() {
        //Finished
        viewModelGameFragment.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections
                .actionGameFragmentToGameFinishedFragment( gameResult )
        )
    }
}