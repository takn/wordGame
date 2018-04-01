package ramirez.nelson.wordgame.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_word_game_main.view.*

/**
 * Created by nelsonramirez on 3/28/18.
 */
class WordBank @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    fun setWord(word: String, sourceLanguage: String) {
        //TODO localize text based on source language
        word_target?.text = "Find the Spanish translations in the grid for the word: ${word.toUpperCase()}"
        word_matches.text = ""
    }

    fun addMatchedWord(word: String) {
        word_matches?.text = word.plus("\n")
    }
}