package ramirez.nelson.wordgame.domain.model

class LetterLocation(val x: Int, val y: Int) {
    val id = "$x,$y"

    override fun toString(): String {
        return id
    }
}