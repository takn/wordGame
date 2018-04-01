package ramirez.nelson.wordgame.presentation

import android.util.Log
import android.view.View
import ramirez.nelson.wordgame.domain.GameInfoUseCase
import ramirez.nelson.wordgame.domain.model.WordGameModel
import ramirez.nelson.wordgame.presentation.views.InfoPanel

/**
 * Created by nelsonramirez on 3/28/18.
 */
class GameInfoPresenter(val gameInfoUseCase: GameInfoUseCase) {
    private lateinit var _infoPanel: InfoPanel

    fun init(infoPanel: InfoPanel) {
        _infoPanel = infoPanel
        gameInfoUseCase.getGameData()?.subscribe { data -> updateInfoPanel(data) }
        gameInfoUseCase.getCurrentGame()
                .subscribe { game -> updateVisibility(game) }

    }

    private fun updateVisibility(game: WordGameModel) {
        Log.d("tag", "update visibility currentGame :: ${game.word} is complete? ${game.isComplete()}")
        var visibility = View.GONE
        if (game.isComplete()) visibility = View.VISIBLE
        _infoPanel.visibility = visibility
    }

    private fun updateInfoPanel(data: List<WordGameModel>?) {
        _infoPanel.init(this, data)
    }

    fun onGameSelected(game: WordGameModel) {
        gameInfoUseCase.onSelectGame(game)
        _infoPanel.visibility = View.GONE

    }
}