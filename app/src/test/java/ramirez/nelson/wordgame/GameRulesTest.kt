package ramirez.nelson.wordgame

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ramirez.nelson.wordgame.domain.GameRules
import ramirez.nelson.wordgame.domain.model.LetterLocation

/**
 * Tests the game rules for matching user selections to pre-defined word targets and letter locations
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameRulesTest {
    private val EXPECTED_MATCH = "itsAMatch"
    private val EXPECTED_OTHER = "anotherMatch"
    private val EXPECTED_EMPTY = ""
    private lateinit var letterLocations: List<LetterLocation>

    private lateinit var sut: GameRules

    @Before
    fun setup() {
        val location1 = LetterLocation(0, 0)
        letterLocations = listOf(location1)
        sut = GameRules()
    }

    @Test
    fun canMatchValidLetterLocation() {
        assertTrue(sut.matchesLetterLocation(LetterLocation(0, 0), 0, letterLocations))
        assertFalse(sut.matchesLetterLocation(LetterLocation(0, 1), 0, letterLocations))
    }

    @Test
    fun doesNotMatchInvalidIndex() {
        val indexOutOfBounds = 1
        val indexTooSmall = -1
        assertFalse(sut.matchesLetterLocation(LetterLocation(0, 0), indexOutOfBounds, letterLocations))
        assertFalse(sut.matchesLetterLocation(LetterLocation(0, 0), indexTooSmall, letterLocations))
    }

    @Test
    fun canMatchMultipleLetterLocations() {
        letterLocations = listOf(LetterLocation(0, 0), LetterLocation(0, 1))
        assertTrue(sut.matchesLetterLocation(LetterLocation(0, 0), 0, letterLocations))
        assertFalse(sut.matchesLetterLocation(LetterLocation(1, 1), 0, letterLocations))

        assertTrue(sut.matchesLetterLocation(LetterLocation(0, 1), 1, letterLocations))
        assertFalse(sut.matchesLetterLocation(LetterLocation(0, 2), 1, letterLocations))

    }


    @Test
    fun canFindWordTargetFromFirstPoint() {
        val wordTargets = mapOf(Pair(EXPECTED_MATCH, letterLocations),
                Pair(EXPECTED_OTHER, listOf(LetterLocation(2, 2))))
        assertEquals(EXPECTED_MATCH, sut.findWordTarget(LetterLocation(0, 0), wordTargets))
        assertEquals(EXPECTED_OTHER, sut.findWordTarget(LetterLocation(2, 2), wordTargets))
        assertEquals(EXPECTED_EMPTY, sut.findWordTarget(LetterLocation(2, 5), wordTargets))
        assertEquals(EXPECTED_EMPTY, sut.findWordTarget(LetterLocation(0, 1), wordTargets))
    }

    @Test
    fun testStartSelection() {
        val wordTargets = mapOf(Pair(EXPECTED_MATCH, letterLocations),
                Pair(EXPECTED_OTHER, listOf(LetterLocation(2, 2))))
        assertTrue(sut.startSelection(LetterLocation(0, 0), wordTargets))
        assertFalse(sut.startSelection(LetterLocation(1, 0), wordTargets))
        assertTrue(sut.startSelection(LetterLocation(2, 2), wordTargets))
    }

    @Test
    fun cannotAddToSelectionWithoutCallingStartSelection() {
        assertFalse(sut.addToSelection(LetterLocation(0, 0)))
    }

    @Test
    fun testAddSelection() {
        letterLocations = listOf(LetterLocation(0, 0), LetterLocation(0, 1), LetterLocation(0, 2))
        val wordTargets = mapOf(Pair(EXPECTED_MATCH, letterLocations))
        assertTrue(sut.startSelection(LetterLocation(0, 0), wordTargets))
        assertFalse(sut.addToSelection(LetterLocation(0, 2)))
        assertTrue(sut.addToSelection(LetterLocation(0, 1)))
        assertFalse(sut.addToSelection(LetterLocation(0, 1)))
        assertTrue(sut.addToSelection(LetterLocation(0, 2)))
        assertFalse(sut.addToSelection(LetterLocation(0, 2)))
        assertFalse(sut.addToSelection(LetterLocation(0, 3)))

        assertEquals(letterLocations.size, sut.letterSelections.size)

    }

    @Test
    fun testIntegration() {
        assertEquals(sut.noMatch, sut.endSelection(LetterLocation(0, 0)))
        letterLocations = listOf(LetterLocation(0, 0), LetterLocation(0, 1), LetterLocation(0, 2))
        val wordTargets = mapOf(Pair(EXPECTED_MATCH, letterLocations))
        assertTrue(sut.startSelection(LetterLocation(0, 0), wordTargets))
        assertTrue(sut.addToSelection(LetterLocation(0, 1)))
        assertTrue(sut.addToSelection(LetterLocation(0, 2)))
        assertEquals(EXPECTED_MATCH, sut.endSelection(LetterLocation(0, 2)))
        assertEquals(sut.noMatch, sut.endSelection(LetterLocation(0, 3)))
    }
}
