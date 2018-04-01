package ramirez.nelson.wordgame.data

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by nelsonramirez on 4/1/18.
 */
class CustomGsonConverter(val gson: Gson, val typeAdapter: WordGameModelTypeAdapter) : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return CustomGsonResponseBodyConverter(gson, typeAdapter)
    }
}