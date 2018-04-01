package ramirez.nelson.wordgame

import android.app.Application
import com.google.gson.GsonBuilder
import ramirez.nelson.wordgame.data.CustomGsonConverter
import ramirez.nelson.wordgame.data.DataService
import ramirez.nelson.wordgame.data.FetchGameDataExecutor
import ramirez.nelson.wordgame.data.WordGameModelTypeAdapter
import ramirez.nelson.wordgame.domain.FetchGameDataUseCase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by nelsonramirez on 3/29/18.
 */
class GameApplication : Application() {


    private lateinit var gameManager: GameManager

    override fun onCreate() {
        super.onCreate()

        /**
         * Note for reviewer, this would typically be injected using dagger.
         */

        var gson = GsonBuilder()
                .setLenient()
                .create()

        var retrofit2 = Retrofit.Builder()
                .baseUrl("https://s3.amazonaws.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomGsonConverter(gson, WordGameModelTypeAdapter()))
                .build()

        var dataService = retrofit2.create(DataService::class.java)

        gameManager = GameManager(FetchGameDataUseCase(FetchGameDataExecutor(dataService)))
        gameManager.init()
    }


    fun getGameManager(): GameManager {
        return gameManager
    }


}