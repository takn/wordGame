package ramirez.nelson.wordgame.data

import io.reactivex.Observable
import ramirez.nelson.wordgame.domain.boundaries.IFetchGameDataExecutor
import ramirez.nelson.wordgame.domain.model.WordGameModel

/**
 * Created by nelsonramirez on 3/28/18.
 */
class FetchGameDataExecutor(private val dataService: DataService) : IFetchGameDataExecutor {
    override fun fetchGameData(): Observable<List<WordGameModel>> {
        return dataService.getGames()
    }
}