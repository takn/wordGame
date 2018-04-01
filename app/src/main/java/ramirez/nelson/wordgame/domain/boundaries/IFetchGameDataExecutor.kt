package ramirez.nelson.wordgame.domain.boundaries

import io.reactivex.Observable
import ramirez.nelson.wordgame.domain.model.WordGameModel

/**
 * Created by nelsonramirez on 3/28/18.
 */
interface IFetchGameDataExecutor {
    fun fetchGameData(): Observable<List<WordGameModel>>
}