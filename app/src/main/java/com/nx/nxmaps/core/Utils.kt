package com.nx.nxmaps.core

import android.util.Log
import com.nx.nxmaps.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception?) = e?.apply {
            Log.e(TAG, stackTraceToString())
        }
    }
}