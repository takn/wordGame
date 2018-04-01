package ramirez.nelson.wordgame.data

import io.reactivex.Observable
import ramirez.nelson.wordgame.domain.model.WordGameModel
import retrofit2.http.GET

/**
 * Created by nelsonramirez on 4/1/18.
 */
interface DataService {
    @GET("duolingo-data/s3/js2/find_challenges.txt")
    fun getGames(): Observable<List<WordGameModel>>
}