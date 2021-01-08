package com.ix.ibrahim7.facebookintegration.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
/*import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy*/
import com.ix.ibrahim7.facebookintegration.R

object Constant {

    const val EMAIL="email"
    const val TAG="eee"
    const val LOGIN="login"
    const val USERID="userid"
    const val CATEGORYID="categoryid"

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

}