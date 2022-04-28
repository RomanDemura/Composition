package tech.demura.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import tech.demura.composition.R
import tech.demura.composition.databinding.FragmentChooseLevelBinding
import tech.demura.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners(){
        binding.buttonLevelTest.setOnClickListener{
            launchGameFragment(Level.TEST)
        }
        binding.buttonLevelEasy.setOnClickListener{
            launchGameFragment(Level.EASY)
        }
        binding.buttonLevelMedium.setOnClickListener {
            launchGameFragment(Level.MEDIUM)
        }
        binding.buttonLevelHard.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

    private fun launchGameFragment(level: Level){
        val bundle = Bundle().apply {
            putParcelable(GameFragment.KEY_LEVEL, level)
        }
        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, bundle)
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFragment.newInstance(level))
//            .addToBackStack(GameFragment.GAME_FRAGMENT)
//            .commit()
    }

    companion object{
        const val CHOOSE_LEVEL_FRAGMENT = "choose_level_fragment"
        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }
}