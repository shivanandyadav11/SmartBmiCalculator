package online.nsandroid.smartbmicalculator.ui.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.PixelCopy
import android.view.View
import androidx.compose.ui.geometry.Rect
import androidx.core.content.FileProvider
import online.nsandroid.smartbmicalculator.model.ImageShareStatus
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

internal fun getBMIResultImage(rect: Rect, view: View, context: Context, imageShareStatus: (ImageShareStatus) -> Unit) {

    try {
        val bitmap = Bitmap.createBitmap(
            rect.width.toInt(),
            rect.height.toInt(),
            Bitmap.Config.ARGB_8888
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PixelCopy.request(
                (context as Activity).window,
                rect.let {
                    android.graphics.Rect(
                        it.left.toInt(),
                        it.top.toInt(),
                        it.right.toInt(),
                        it.bottom.toInt()
                    )
                },
                bitmap,
                {
                    when (it) {
                        PixelCopy.SUCCESS -> {
                            imageShareStatus.invoke(ImageShareStatus.Success(bitmap))
                        }

                        else -> {
                            imageShareStatus.invoke(ImageShareStatus.Failure)
                        }
                    }

                },
                Handler(Looper.getMainLooper())
            )

        } else {
            val canvas = Canvas(bitmap)
                .apply {
                    translate(-rect.left, -rect.top)
                }
            view.draw(canvas)
            canvas.setBitmap(null)
            imageShareStatus.invoke(ImageShareStatus.Success(bitmap))
        }

    } catch (ex: Exception) {
        Log.d("BMI Result Screen", " ${ex.message}")
    }
}

fun shareImage(context: Context, bitmap: Bitmap?) {
    fun getUri(context: Context, bitmap: Bitmap): Uri? {
        val path: String = context.externalCacheDir.toString() + "/yourBMIResult.jpg"
        val out: OutputStream?
        val file = File(path)
        try {
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return FileProvider.getUriForFile(
            context, context.packageName + ".provider", file
        )
    }
    val imageUri: Uri? = bitmap?.let { getUri(context, it) }
    val chooserIntent = Intent(Intent.ACTION_SEND)
    chooserIntent.type = "image/*"
    chooserIntent.putExtra(Intent.EXTRA_TEXT, "Your Calculated BMI")
    chooserIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    try {
        context.startActivity(chooserIntent)
    } catch (_: Exception) {
    }
}