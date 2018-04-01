package ramirez.nelson.wordgame.presentation.utils

import android.content.Context
import android.content.res.Configuration

/**
 * Created by nelsonramirez on 3/29/18.
 */
class DimenUtils {
    fun calculateTileDimension(context: Context, numColumns: Int): Int {

        val displayMetrics = context.resources.displayMetrics
        var dpWidth = displayMetrics.widthPixels
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            dpWidth = displayMetrics.heightPixels - getStatusBarHeight()
        }
        return dpWidth / numColumns
    }

    private fun getStatusBarHeight(): Int {
        //TODO get status bar height
        return 70;
    }
}