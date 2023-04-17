package com.example.hangman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.hangman.databinding.FragmentGameBinding
import com.example.hangman.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
 //       return inflater.inflate(R.layout.fragment_title, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener {
            //Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
            Navigation.findNavController(it).navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        return binding.root
    }


}