package ramirez.nelson.wordgame

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import ramirez.nelson.wordgame.domain.FetchGameDataUseCase
import ramirez.nelson.wordgame.domain.model.WordGameModel

/**
 * Created by nelsonramirez on 3/29/18.
 */
class GameManager(val fetchGameDataUseCase: FetchGameDataUseCase) {


    //TODO fetch as observable
    private lateinit var gameData: List<WordGameModel>

    private lateinit var currentGame: WordGameModel

    var gameDataObservable: Observable<List<WordGameModel>> = Observable.empty()
    var currentGameObservable: ReplaySubject<WordGameModel> = ReplaySubject.create<WordGameModel>()

    fun init() {
        gameDataObservable = fetchGameDataUseCase.fetchGameData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    //observable.
    fun selectGame(wordGameModel: WordGameModel) {
        currentGame = wordGameModel
        currentGameObservable.onNext(currentGame)
    }

    fun addMatchedWord(match: String) {
        currentGame.addMatchedWord(match)
        if (currentGame.isComplete()) {
            currentGameObservable.onNext(currentGame)
        }
    }


}