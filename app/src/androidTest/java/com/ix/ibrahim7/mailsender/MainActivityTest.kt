package com.ix.ibrahim7.mailsender

import android.content.Intent
import android.os.SystemClock
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ix.ibrahim7.mailsender.ui.activity.MainActivity
import com.ix.ibrahim7.mailsender.ui.fragment.main.ListCategoryFragment.Companion.data
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    public var activityRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var activitySearchScenario: ActivityScenario<MainActivity>

    @Test
    fun recyclerViewTest() {

        SystemClock.sleep(4000)

        onView(withId(R.id.listCategoryFragment))
            .perform(click())

        onView(withId(R.id.list_Category))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<RecyclerView.ViewHolder>(
                        data.size - 1
                    )
            )

        onView(withId(R.id.add))
            .perform(click())

        onView(withId(R.id.txtName))
            .perform(click())
            .perform(
                ViewActions.typeText("Ibrahem"),
                ViewActions.closeSoftKeyboard()
            );

        onView(withId(R.id.btn_Save))
            .perform(click())

        SystemClock.sleep(2000)

        onView(withId(R.id.list_Category))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        data.size - 1, click()
                    )
            )

    }


    @Test
    fun sendEmail() {
        val i = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        i.putExtra("email", "Ibra@gmail.com")
        i.putExtra(
            "note",
            "Instrumented test, which will execute on an Android device.Instrumented test, which will execute on an Android device."
        )


        activitySearchScenario = ActivityScenario.launch(i)
        SystemClock.sleep(2000)

        //Nav
        onView(withId(R.id.add))
            .perform(click())

        onView(withId(R.id.txtEmailTo))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("email")),
                ViewActions.closeSoftKeyboard()
            )

        onView(withId(R.id.txtEmailNote))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("note")),
                ViewActions.closeSoftKeyboard()
            )

        //Buttom
        onView(withId(R.id.btn_Save))
            .perform(click())

        SystemClock.sleep(2000)
    }

    @Test
    fun goToDetailsCategory() {
        val i = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        i.putExtra("name", "Noor Yasser")
        i.putExtra(
            "email",
            "noor1yasser9@gmail.com"
        )
        activitySearchScenario = ActivityScenario.launch(i)
        SystemClock.sleep(2000)

        onView(withId(R.id.listCategoryFragment))
            .perform(click())


        SystemClock.sleep(1000)

        onView(withId(R.id.list_Category))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        data.size - 1, click()
                    )
            )
        SystemClock.sleep(1000)

        onView(withId(R.id.tvAddEmail))
            .perform(click())

        SystemClock.sleep(1000)

        onView(withId(R.id.txtName))
            .perform(click())
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("name")),
                ViewActions.closeSoftKeyboard()
            )

        onView(withId(R.id.txtEmail))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("email")),
                ViewActions.closeSoftKeyboard()
            )

        onView(withId(R.id.btn_Save))
            .perform(click())

        SystemClock.sleep(2000)

        Espresso.pressBack()

        onView(withId(R.id.settingsFragment))
            .perform(click())


        SystemClock.sleep(2000)




    }
}