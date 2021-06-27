package com.ix.ibrahim7.mailsender.other

import android.app.*
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.mailsender.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tourguide.tourguide.TourGuide
import java.io.*
import java.util.*


fun Activity.getSnackBar(@LayoutRes layoutId: Int, view: View, message: String): View {
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)

    val custom = this.layoutInflater.inflate(layoutId, null)

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackbarLayout.setPadding(0, 0, 0, 180)

    custom.findViewById<com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar>(R.id.item_progress).progress = 100f

    custom.findViewById<TextView>(R.id.txtMessage).text = message

    snackbarLayout.addView(custom)
    snackbar.show()
    return custom
}

fun getSharePref(context: Context) =
    context.getSharedPreferences("Share", Activity.MODE_PRIVATE)

fun editor(context: Context) = getSharePref(context).edit()


lateinit var dialog: Dialog
fun showDialog(activity: Activity) {
    dialog = Dialog(activity).apply {
        setContentView(R.layout.dialog_loading)
        setCancelable(true)
    }
    dialog.show()
}

fun setImage(context: Context, url: Any?, iv: ImageView, ivPaceHolder: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(ivPaceHolder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(iv)
}


fun enableTips(activity: Activity,description: String,view: View,gravity: Int,duration: Long,textcolor:Int){
    GlobalScope.launch (Dispatchers.Main){
        val mTourGuideHandler: TourGuide = TourGuide.create(activity) {
            toolTip {
                description { description }
                textColor { textcolor }
                backgroundColor { ContextCompat.getColor(activity,R.color.secondery_background_color) }
                shadow { true }
                gravity { gravity }
            }
        }.playOn(view)
        delay(duration)
        mTourGuideHandler.cleanUp()
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun setUpStatusBar(activity: Activity, types: Int) {
    val window: Window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    if (types == 1) {
        window.statusBarColor = ContextCompat.getColor(activity, R.color.background_color)
    } else {
        window.statusBarColor = ContextCompat.getColor(activity, R.color.background_color)
    }
}