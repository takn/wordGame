package ramirez.nelson.wordgame.presentation

import ramirez.nelson.wordgame.GameManager
import ramirez.nelson.wordgame.domain.GameActionsUseCase
import ramirez.nelson.wordgame.domain.IGameActions
import ramirez.nelson.wordgame.domain.model.LetterLocation
import ramirez.nelson.wordgame.domain.model.WordGameModel
import ramirez.nelson.wordgame.presentation.views.GameGrid
import ramirez.nelson.wordgame.presentation.views.WordBank

/**
 * Created by nelsonramirez on 3/28/18.
 */
class GamePresenter(private val gameActions: GameActionsUseCase,
                    private val gameManager: GameManager) : IGameActions {
    private var wordLocations: Map<String, List<LetterLocation>>? = emptyMap()

    fun startSelection(letterLocation: LetterLocation): Boolean = startSelection(letterLocation, wordLocations.orEmpty())

    override fun startSelection(letterLocation: LetterLocation, wordTargets: Map<String,
            List<LetterLocation>>): Boolean =
            gameActions.startSelection(letterLocation, wordLocations.orEmpty())

    override fun addToSelection(letterLocation: LetterLocation): Boolean = gameActions.addToSelection(letterLocation)

    override fun endSelection(letterLocation: LetterLocation): String {
        var match = gameActions.endSelection(letterLocation)
        gameManager.addMatchedWord(match)
        wordBank?.addMatchedWord(match)
        return match
    }


    private var gridLayout: GameGrid? = null

    private var wordBank: WordBank? = null

    fun init(grid_layout: GameGrid, word_bank: WordBank) {
        gridLayout = grid_layout
        wordBank = word_bank
        gameManager.currentGameObservable.subscribe { game -> onCurrentGameUpdate(game) }
    }


    private fun onCurrentGameUpdate(gameModel: WordGameModel) {
        if (!gameModel.isComplete()) {
            wordLocations = gameModel.wordLocations
            gridLayout?.createGrid(this, gameModel.characterGrid)
            wordBank?.setWord(gameModel.word, gameModel.sourceLanguage)
        }
    }
}