package com.example.hangman

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class GameViewModel(application: Application) : AndroidViewModel(application) {


    private var counter: Int = 0
    private var countertwo: Int = 0

    lateinit var word: String
    private var alphabet: String = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    private val words: Array<String> = application.resources.getStringArray(R.array.words)

    private lateinit var partOfWord: StringBuilder

    private var _eventGameWon = MutableLiveData(false)
    val eventGameWon: LiveData<Boolean>
        get() = _eventGameWon

    private var _eventGameLose = MutableLiveData(false)
    val eventGameLose: LiveData<Boolean>
        get() = _eventGameLose

    private var _image = MutableLiveData(R.drawable.image0)
    val image: LiveData<Int>
        get() = _image

    private var _keyboard: MutableList<LiveData<KeyboardItem>> = mutableListOf()
    val keyboard: List<LiveData<KeyboardItem>>
        get() = _keyboard

    private var _anotherWord: MutableList<LiveData<WordItem>> = mutableListOf()
    val anotherWord: List<LiveData<WordItem>>
        get() = _anotherWord

    init {
        newWord()
        keyboardGeneration()
        wordGeneration()
    }

    private fun keyboardGeneration() {
        for (c in alphabet) {
            _keyboard.add(MutableLiveData(KeyboardItem(KeyboardState.NOT_PRESSED, c.toString())))
        }
    }

    private fun wordGeneration() {
        for (c in word) {
            _anotherWord.add(MutableLiveData(WordItem(WordState.INVISIBLE, c.toString())))
        }
        _anotherWord[0].value?.checked = WordState.VISIBLE
        _anotherWord[_anotherWord.size - 1].value?.checked = WordState.VISIBLE
    }

    private fun newWord() {

        words.shuffle()
        word = words[0]
        partOfWord = StringBuilder(word)
        partOfWord.deleteAt(word.length - 1)
        partOfWord.deleteAt(0)
        partOfWord.toString()
    }

    fun isCorrect(index: Int) {

        if (_keyboard[index].value?.letter.toString() in partOfWord) {
            _keyboard[index].value?.checked = KeyboardState.CORRECT
            for (i in 1 until _anotherWord.size - 1) {
                if (_anotherWord[i].value?.letter == _keyboard[index].value?.letter) {
                    _anotherWord[i].value?.checked = WordState.VISIBLE
                    countertwo += 1
                }
            }
            if (countertwo >= partOfWord.length) {
                _eventGameWon.value = true
            }
        } else {
            if (counter == 5) {
                _eventGameLose.value = true
            }
            counter += 1
            _image.value = when (counter) {
                1 -> R.drawable.image1
                2 -> R.drawable.image2
                3 -> R.drawable.image3
                4 -> R.drawable.image4
                5 -> R.drawable.image5
                else -> R.drawable.image6
            }
            _keyboard[index].value?.checked = KeyboardState.INCORRECT
        }
    }

    fun onGameWonComplete() {
        _eventGameWon.value = false
    }

    fun onGameLoseComplete() {
        _eventGameLose.value = false
    }
}