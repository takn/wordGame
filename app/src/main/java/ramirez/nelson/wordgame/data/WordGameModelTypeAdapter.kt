package ramirez.nelson.wordgame.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import ramirez.nelson.wordgame.domain.model.GridTile
import ramirez.nelson.wordgame.domain.model.LetterLocation
import ramirez.nelson.wordgame.domain.model.TileState
import ramirez.nelson.wordgame.domain.model.WordGameModel
import java.util.*

class WordGameModelTypeAdapter : TypeAdapter<List<WordGameModel>>() {
    override fun write(out: JsonWriter?, value: List<WordGameModel>?) {
        //no op
    }

    /**
     * We deal with our multi-root node json with a custom type adapter..
     * which gives us the added benefit of allowing us to transform some of the data to
     * our liking
     */
    override fun read(reader: JsonReader?): List<WordGameModel> {
        reader?.isLenient = true
        return readGames(reader!!)
    }


    private fun readGames(reader: JsonReader): List<WordGameModel> {
        var gameList = mutableListOf<WordGameModel>()

        var _sourceLanguage = ""
        var _targetLanguage: String
        var _word = ""
        var _gridData = emptyList<List<GridTile>>()
        var _wordLocations = emptyMap<String, List<LetterLocation>>()
        while (reader.hasNext()) {
            var token = reader.peek()
            when (token) {
                JsonToken.BEGIN_ARRAY -> {
                    reader.beginArray()
                }
                JsonToken.BEGIN_OBJECT -> {
                    reader.beginObject()
                }

                JsonToken.END_OBJECT -> {
                    reader.endObject()
                    println("END OBJECT")
                }

                JsonToken.NAME -> {
                    var n = reader.nextName()
                    when (n) {
                        "source_language" -> {
                            _sourceLanguage = reader.nextString()
                        }
                        "word" -> {
                            _word = reader.nextString()
                        }

                        "character_grid" -> {
                            _gridData = readCharacterGrid(reader)
                        }
                        "word_locations" -> {
                            _wordLocations = readWordLocations(reader)
                        }
                        "target_language" -> {
                            _targetLanguage = reader.nextString()
                            if (reader.peek() == JsonToken.END_OBJECT) {
                                reader.endObject()
                                gameList.add(WordGameModel(_sourceLanguage, _word,
                                        _gridData, _wordLocations, _targetLanguage))
                            }
                        }
                    }

                }
                JsonToken.STRING -> {
                    reader.nextString()
                }

                JsonToken.NUMBER -> {
                    reader.nextInt()
                }

                JsonToken.BOOLEAN -> {
                    reader.nextBoolean()
                }

                JsonToken.NULL -> {
                    reader.nextNull()
                }

                JsonToken.END_DOCUMENT -> {
                    println("END DOCUMENT")
                    return gameList
                }
            }
        }
        return gameList
    }

    private fun readWordLocations(reader: JsonReader): Map<String, List<LetterLocation>> {
        var locationsMap = mutableMapOf<String, List<LetterLocation>>()
        reader.beginObject()
        while (reader.hasNext()) {
            var n = reader.nextName()
            var s = reader.nextString()
            locationsMap.put(s, toLetterLocation(n))
        }
        reader.endObject()
        return locationsMap
    }

    private fun toLetterLocation(letterString: String): List<LetterLocation> {
        //TODO this is a paste from java.. look how ugly it is! it can be prettier
        val arr = letterString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val letterLocations = ArrayList<LetterLocation>()
        var i = 0
        while (i < arr.size - 1) {
            letterLocations.add(LetterLocation(Integer.valueOf(arr[i])!!,
                    Integer.valueOf(arr[i + 1])!!))
            i += 2
        }
        return letterLocations
    }

    private fun readCharacterGrid(reader: JsonReader): List<List<GridTile>> {
        reader.beginArray()
        var gridList = mutableListOf<List<GridTile>>()
        while (reader.hasNext()) {
            gridList.add(readGridTiles(reader))

        }
        reader.endArray()
        return gridList
    }

    private fun readGridTiles(reader: JsonReader): List<GridTile> {
        reader.beginArray()
        var tileList = mutableListOf<GridTile>()
        while (reader.hasNext()) {
            var s = reader.nextString()
            tileList.add(GridTile(s, TileState.DEFAULT))
        }
        reader.endArray()
        return tileList

    }
}