package ramirez.nelson.wordgame.domain

import ramirez.nelson.wordgame.domain.model.LetterLocation

class GameRules {
    val noMatch = ""
    private var currentWordTarget: String = noMatch
    private var letterTargets: List<LetterLocation> = emptyList()
    internal var letterSelections: MutableMap<String, LetterLocation> = mutableMapOf()

    private var selectionStarted: Boolean = false

    fun startSelection(letterLocation: LetterLocation,
                       wordTargets: Map<String, List<LetterLocation>>): Boolean {
        currentWordTarget = findWordTarget(letterLocation, wordTargets)
        letterSelections.clear()
        letterTargets = wordTargets[currentWordTarget].orEmpty()
        return if (currentWordTarget == noMatch) false else {
            letterSelections[letterLocation.id] = letterLocation
            selectionStarted = true
            return true
        }
    }

    fun addToSelection(letterLocation: LetterLocation): Boolean {
        if (selectionStarted && letterTargets.isNotEmpty() &&
                matchesLetterLocation(letterLocation, letterSelections.size, letterTargets)) {
            letterSelections[letterLocation.id] = letterLocation
            return true
        }
        return false
    }

    fun endSelection(letterLocation: LetterLocation): String {
        selectionStarted = false
        if (letterTargets.isNotEmpty()
                && matchesLetterLocation(letterLocation,
                        letterSelections.size - 1, letterTargets)
                && letterSelections.size == letterTargets.size) {
            letterSelections.clear()
            return currentWordTarget
        }
        letterSelections.clear()
        return noMatch
    }

    internal fun findWordTarget(letterLocation: LetterLocation,
                                wordTargets: Map<String, List<LetterLocation>>): String {
        wordTargets.forEach { (key, value) ->
            run {
                if (matchesLetterLocation(letterLocation, 0, value)) {
                    return key
                }

            }
        }
        return noMatch
    }

    internal fun matchesLetterLocation(letterLocation: LetterLocation, index: Int,
                                       letterLocations: List<LetterLocation>): Boolean {
        if (index >= letterLocations.size || index < 0) return false
        return letterLocations[index].x == letterLocation.x &&
                letterLocations[index].y == letterLocation.y
    }


}