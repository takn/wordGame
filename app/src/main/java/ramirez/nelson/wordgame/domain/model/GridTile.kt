package ramirez.nelson.wordgame.domain.model

/**
 * Created by nelsonramirez on 3/29/18.
 */
class GridTile(val value: String, var state: TileState) {
    override fun toString(): String {
        return "value: $value state $state"
    }
}

enum class TileState { DEFAULT, MATCHED }