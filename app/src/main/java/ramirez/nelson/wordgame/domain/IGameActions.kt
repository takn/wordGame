package ramirez.nelson.wordgame.domain

import ramirez.nelson.wordgame.domain.model.LetterLocation

/**
 * Created by nelsonramirez on 3/28/18.
 */
interface IGameActions {
    fun startSelection(letterLocation: LetterLocation, wordTargets: Map<String, List<LetterLocation>>): Boolean
    fun addToSelection(letterLocation: LetterLocation): Boolean
    fun endSelection(letterLocation: LetterLocation): String
}