package com.example.hangman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.hangman.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    private lateinit var binding:FragmentGameWonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_game_won, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)

        binding.againButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "Отгаданное слово: ${args.word}", Toast.LENGTH_LONG).show()

        return binding.root
    }


}