package ramirez.nelson.wordgame.domain

import io.reactivex.Observable
import ramirez.nelson.wordgame.GameManager
import ramirez.nelson.wordgame.domain.model.WordGameModel

/**
 * Created by nelsonramirez on 3/31/18.
 */
class GameInfoUseCase(private val gameManager: GameManager) {

    fun onSelectGame(wordGameModel: WordGameModel) = gameManager.selectGame(wordGameModel)

    fun getGameData(): Observable<List<WordGameModel>>? = gameManager.gameDataObservable

    fun getCurrentGame(): Observable<WordGameModel> = gameManager.currentGameObservable

}