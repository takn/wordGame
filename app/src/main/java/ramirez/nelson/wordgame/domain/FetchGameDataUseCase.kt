package ramirez.nelson.wordgame.domain

import io.reactivex.Observable
import ramirez.nelson.wordgame.domain.boundaries.IFetchGameDataExecutor
import ramirez.nelson.wordgame.domain.model.WordGameModel

/**
 * Created by nelsonramirez on 3/28/18.
 */
class FetchGameDataUseCase(private val fetchGameDataExecutor: IFetchGameDataExecutor) {
    fun fetchGameData(): Observable<List<WordGameModel>> = fetchGameDataExecutor.fetchGameData()
}