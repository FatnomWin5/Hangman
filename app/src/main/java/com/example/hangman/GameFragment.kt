package com.example.hangman

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hangman.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("TAG Hangman", "onCreate")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        gridKeyboard()
        linearWord()

        viewModel.eventGameWon.observe(viewLifecycleOwner) {
            if (it) {
                gameWon()
                viewModel.onGameWonComplete()
            }
        }

        viewModel.eventGameLose.observe(viewLifecycleOwner) {
            if (it) {
                gameLose()
                viewModel.onGameLoseComplete()
            }
        }

        viewModel.image.observe(viewLifecycleOwner) {
            binding.image.setImageResource(it)
        }

        return binding.root
    }

    private fun gridKeyboard() {
        for ((i, key) in viewModel.keyboard.withIndex()) {

            val button = TextView(context)

            button.setOnClickListener {
                viewModel.isCorrect(i)
                button.setOnClickListener(null)
            }

            button.textSize = 50F
            button.text = key.value?.letter.toString()
            button.gravity = Gravity.CENTER
            button.layoutParams = ViewGroup.LayoutParams(
                Resources.getSystem().displayMetrics.widthPixels / 10,
                Resources.getSystem().displayMetrics.widthPixels / 6
            )

            binding.gridlayout.addView(button)

            key.observe(viewLifecycleOwner) {
                when (it.checked) {
                    KeyboardState.CORRECT -> button.setTextColor(Color.GREEN)
                    KeyboardState.INCORRECT -> button.setTextColor(Color.RED)
                    KeyboardState.NOT_PRESSED -> button.setTextColor(Color.BLACK)
                }
            }
        }
    }

    private fun linearWord() {

        binding.word.removeAllViews()

        for (key in viewModel.anotherWord) {
            val letter = TextView(context)
            letter.text = key.value?.letter.toString()
            letter.setAutoSizeTextTypeUniformWithConfiguration(
                5,
                400,
                2,
                TypedValue.COMPLEX_UNIT_DIP
            )

            letter.setBackgroundResource(R.drawable.down)
            letter.gravity = Gravity.CENTER
            letter.layoutParams =
                ViewGroup.LayoutParams(
                    Resources.getSystem().displayMetrics.widthPixels / (viewModel.anotherWord.size * 3 / 2),
                    Resources.getSystem().displayMetrics.widthPixels / 8
                )

            binding.word.addView(letter)

            key.observe(viewLifecycleOwner) {
                when (it.checked) {
                    WordState.VISIBLE -> letter.setTextColor(Color.BLACK)
                    WordState.INVISIBLE -> letter.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun gameWon() {
        view?.findNavController()
            ?.navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(viewModel.word))
    }

    private fun gameLose() {
        view?.findNavController()
            ?.navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(viewModel.word))
    }
}

data class KeyboardItem(var checked: KeyboardState, val letter: String)
enum class KeyboardState { CORRECT, INCORRECT, NOT_PRESSED }

data class WordItem(var checked: WordState, val letter: String)
enum class WordState { VISIBLE, INVISIBLE }