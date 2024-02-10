package online.nsandroid.smartbmicalculator.model

import android.graphics.Bitmap

sealed interface ImageShareStatus {
    class Success(val bitmap: Bitmap) : ImageShareStatus
    data object Failure: ImageShareStatus
}