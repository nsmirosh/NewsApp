package nick.mirosh.newsapp.utils

import android.util.Log
import nick.mirosh.newsapp.BuildConfig

object MyLogger {
    fun e(tag: String?, msg: String?) {
        if (!BuildConfig.DEBUG) {
            Log.e(tag, msg!!)
        }
    }
}