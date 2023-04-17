package com.example.hangman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.hangman.databinding.FragmentGameOverBinding


class GameOverFragment : Fragment() {

    private lateinit var binding:FragmentGameOverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_game_over, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)

        binding.againButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameOverFragment_to_titleFragment)
        }

        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "Это было слово: ${args.word}", Toast.LENGTH_LONG).show()

        return binding.root
    }


}