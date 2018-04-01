package ramirez.nelson.wordgame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_word_game_main.*
import kotlinx.android.synthetic.main.info_panel.*
import ramirez.nelson.wordgame.domain.GameActionsUseCase
import ramirez.nelson.wordgame.domain.GameInfoUseCase
import ramirez.nelson.wordgame.domain.GameRules
import ramirez.nelson.wordgame.presentation.GameInfoPresenter
import ramirez.nelson.wordgame.presentation.GamePresenter


class WordGameActivity : AppCompatActivity() {

    private lateinit var gamePresenter: GamePresenter


    private lateinit var gameInfoPresenter: GameInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var app: GameApplication = application as GameApplication

        setContentView(R.layout.activity_word_game_main)

        //TODO the gameManager would just be injected
        gameInfoPresenter = GameInfoPresenter(GameInfoUseCase(app.getGameManager()))
        gameInfoPresenter.init(info_panel)

        gamePresenter = GamePresenter(GameActionsUseCase(GameRules()), app.getGameManager())
        gamePresenter.init(grid_layout, word_bank)


        //TODO retrofit and make cancellable

//        gamePresenter.startGame(data)


    }


}
