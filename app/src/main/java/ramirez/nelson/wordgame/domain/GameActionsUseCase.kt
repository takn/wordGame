package ramirez.nelson.wordgame.domain

import ramirez.nelson.wordgame.domain.model.LetterLocation

/**
 * Created by nelsonramirez on 3/28/18.
 */
class GameActionsUseCase(private val gameRules: GameRules) : IGameActions {


    override fun startSelection(letterLocation: LetterLocation,
                                wordTargets: Map<String, List<LetterLocation>>): Boolean =
            gameRules.startSelection(letterLocation, wordTargets)

    override fun addToSelection(letterLocation: LetterLocation): Boolean =
            gameRules.addToSelection(letterLocation)

    override fun endSelection(letterLocation: LetterLocation): String =
            gameRules.endSelection(letterLocation)
}
