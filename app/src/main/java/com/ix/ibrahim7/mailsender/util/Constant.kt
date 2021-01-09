package com.ix.ibrahim7.mailsender.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
/*import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy*/
import com.ix.ibrahim7.mailsender.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tourguide.tourguide.TourGuide
import java.time.Duration

object Constant {

    const val EMAIL="email"
    const val TAG="eee"
    const val LOGIN="login"
    const val USERID="userid"
    const val CATEGORYID="categoryid"
    const val HOME="home"
    const val CATEGORY="category"
    const val USERLIST="userlist"
    const val DAYFORMAT="dd/MM/yyyy hh:mm a"
    const val DURATION=2000

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
                    backgroundColor {ContextCompat.getColor(activity,R.color.secondery_background_color) }
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

}