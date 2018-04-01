package ramirez.nelson.wordgame.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by nelsonramirez on 3/28/18.
 */
data class WordGameModel(
        @SerializedName("source_language") val sourceLanguage: String,
        @SerializedName("word") val word: String,
        @SerializedName("character_grid") val characterGrid: List<List<GridTile>>,
        @SerializedName("word_locations") val wordLocations: Map<String, List<LetterLocation>>,
        @SerializedName("target_language") val targetLanguage: String) {
    var foundWordTargets: MutableList<String> = mutableListOf()

    fun isComplete(): Boolean {
        return foundWordTargets.size == wordLocations.size
    }

    fun addMatchedWord(word: String) {
        if (word.isNotEmpty()) {
            foundWordTargets.add(word)
        }
    }
}

