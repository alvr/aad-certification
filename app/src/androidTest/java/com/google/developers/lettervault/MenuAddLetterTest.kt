package com.google.developers.lettervault

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.developers.lettervault.ui.add.AddLetterActivity
import com.google.developers.lettervault.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MenuAddLetterTest {

    @Rule
    @JvmField
    val homeActivityTestRule = IntentsTestRule(HomeActivity::class.java)

    /**
     * With the module `espresso-intents` is possible to check if a component with a name is shown
     * on the screen.
     */
    @Test
    fun clickOnMenuAddLetter_Intent() {
        onView(withId(R.id.action_add))
            .check(matches(isDisplayed()))
            .perform(click())

        intended(hasComponent(AddLetterActivity::class.java.name))
    }

    /**
     * Two elements that the activity [AddLetterActivity] has are the menu `Add` and `Time`.
     * Check if both of the menus are displayed.
     */
    @Test
    fun clickOnMenuAddLetter_View() {
        onView(withId(R.id.action_add))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.action_time))
            .check(matches(isDisplayed()))

        onView(withId(R.id.action_save))
            .check(matches(isDisplayed()))

        onView(withId(R.id.letter_subject))
            .check(matches(isDisplayed()))

        onView(withId(R.id.letter_content))
            .check(matches(isDisplayed()))
    }

}