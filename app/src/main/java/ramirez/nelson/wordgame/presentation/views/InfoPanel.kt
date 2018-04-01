package ramirez.nelson.wordgame.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.info_panel.view.*
import ramirez.nelson.wordgame.R
import ramirez.nelson.wordgame.domain.model.WordGameModel
import ramirez.nelson.wordgame.presentation.GameInfoPresenter

/**
 * Created by nelsonramirez on 3/28/18.
 */
class InfoPanel @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    fun init(presenter: GameInfoPresenter, data: List<WordGameModel>?) {
        games_container.removeAllViews()

        data?.forEach {
            var textView = TextView(context)
            textView.text = it.word
            textView.setTag(R.id.info_panel, it)
            textView.setOnClickListener({ v ->
                var model = v.getTag(R.id.info_panel) as WordGameModel
                presenter.onGameSelected(model)
            })
            games_container.addView(textView)
        }

    }
}