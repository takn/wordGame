package ramirez.nelson.wordgame.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import ramirez.nelson.wordgame.R
import ramirez.nelson.wordgame.domain.model.GridTile
import ramirez.nelson.wordgame.domain.model.LetterLocation
import ramirez.nelson.wordgame.domain.model.TileState
import ramirez.nelson.wordgame.presentation.GamePresenter
import ramirez.nelson.wordgame.presentation.utils.DimenUtils

private val matchingColor = "#0000FF"
private val matchedColor = "#00FF00"
private val defaultColor = "#000000"

/**
 * Created by nelsonramirez on 3/28/18.
 * Note to reviewers. I opted for a GridLayout instead of a recyclerView. I was curious about how much
 * more/less code it would be. I feel like i have a bit more control over the views this way.
 * Not entirely happy with the approach but it was interesting to try.
 */
class GameGrid @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr), View.OnTouchListener {
    private var lastId: Int = -1

    private var selectedTiles: MutableList<View> = mutableListOf()


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                var coordinates: Pair<Int, Int> = toCoordinates(event)
                lastId = toId(coordinates.first, coordinates.second)
                var child = this.findViewById<View>(lastId)
                if (child != null && !viewIsInMatchedState(child) &&
                        presenter.startSelection(child.getTag(R.id.word_location) as LetterLocation)) {
                    selectedTiles.add(child)
                    child.setBackgroundColor(Color.parseColor(matchingColor))
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var coordinates: Pair<Int, Int> = toCoordinates(event)
                var newId = toId(coordinates.first, coordinates.second)
                if (newId != lastId) {
                    lastId = newId
                    var child = this.findViewById<View>(lastId)
                    if (child != null) {
                        var letterLocation = child.getTag(R.id.word_location) as LetterLocation
                        if (presenter.addToSelection(letterLocation)) {
                            selectedTiles.add(child)
                            child.setBackgroundColor(Color.parseColor(matchingColor))
                        }
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                var coordinates: Pair<Int, Int> = toCoordinates(event)
                var child = this.findViewById<ViewGroup>(toId(coordinates.first, coordinates.second))

                if (child != null) {
                    var word = ""
                    if (!viewIsInMatchedState(child)) {
                        word = presenter.endSelection(child.getTag(R.id.word_location) as LetterLocation)
                        if (word.isNotEmpty()) {
                            setTilesSelected(matchedColor, TileState.MATCHED)
                        } else {
                            setTilesSelected(defaultColor, TileState.DEFAULT)
                        }
                    }
                    selectedTiles.clear()
                }

            }
        }
        return true
    }

    private fun viewIsInMatchedState(child: View): Boolean {
        val gridTile: GridTile = child.getTag(R.id.tile_state) as GridTile
        return gridTile.state == TileState.MATCHED
    }

    private fun setTilesSelected(color: String, state: TileState) {
        selectedTiles.forEach { view ->
            val gridTile: GridTile = view.getTag(R.id.tile_state) as GridTile
            gridTile.state = state
            view.setTag(R.id.tile_state, gridTile)
            view.setBackgroundColor(Color.parseColor(color))
        }
    }

    private fun toCoordinates(event: MotionEvent): Pair<Int, Int> {
        var column = event.x.toInt() / tileDimen
        var row = event.y.toInt() / tileDimen
        return Pair(column, row)
    }

    private fun toId(column: Int, row: Int): Int {
        return row * columnCount + row + column
    }


    private lateinit var presenter: GamePresenter


    private var tileDimen: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    fun createGrid(gamePresenter: GamePresenter, gridData: List<List<GridTile>>) {
        removeAllViews()
        presenter = gamePresenter
        var columns = getNumColumns(gridData)
        this.setOnTouchListener(this)
        columnCount = columns
        rowCount = columns
        tileDimen = DimenUtils().calculateTileDimension(context, getNumColumns(gridData))
        gridData.forEachIndexed { rowIndex, gridTiles ->
            gridTiles.forEachIndexed { columnIndex, gridTile ->
                //TODO move letterlocation inside of gridTile
                var letterLocation = LetterLocation(columnIndex, rowIndex)
                var view: ViewGroup = View.inflate(context, R.layout.tile_view, null) as ViewGroup
                view.id = toId(columnIndex, rowIndex)
                view.setTag(R.id.word_location, letterLocation)
                view.setTag(R.id.tile_state, gridTile)

                var colorStr = defaultColor
                if (gridTile.state.equals(TileState.MATCHED)) {
                    colorStr = matchedColor
                }

                view.setBackgroundColor(Color.parseColor(colorStr))
                var txt = view.findViewById<TextView>(R.id.textView)
                txt.text = gridTile.value
                addView(view)
                view.layoutParams.width = tileDimen
                view.layoutParams.height = tileDimen
            }
        }


    }

    private fun getNumColumns(gridData: List<List<GridTile>>): Int {
        //assumes that all arrays contained by this list are the same size
        return if (gridData.isEmpty()) 1 else gridData[0].size

    }
}
