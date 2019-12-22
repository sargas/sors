package net.neoturbine.sors

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class EndToEndFlowTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun endToEndFlow() {
        onView(withText(R.string.welcome_text)).check(matches(isDisplayed()))
        onView(withText(R.string.next_button)).perform(click())

        onView(withText(R.string.filing_status_chooser_help_text)).check(matches(isDisplayed()))
        onView(withText(R.string.next_button)).check(matches(not(isEnabled()))).perform(click())
        // Still on same screen
        onView(withText(R.string.filing_status_chooser_help_text)).check(matches(isDisplayed()))

        // Actually select filing status
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<MyFilingStatusChooserRecyclerViewAdapter.ViewHolder>(0, click()))
        onView(withText(R.string.next_button)).check(matches(isEnabled())).perform(click())

        onView(withHint(R.string.taxable_income_hint)).check(matches(isDisplayed()))
        onView(withText(R.string.next_button)).check(matches(not(isEnabled()))).perform(click())
        // Still on same screen
        onView(withHint(R.string.taxable_income_hint)).check(matches(isDisplayed()))

        // Enter taxable income
        onView(withHint(R.string.taxable_income_hint)).check(matches(isDisplayed())).perform(typeText("50000"), closeSoftKeyboard())
        onView(withText(R.string.next_button)).check(matches(isEnabled())).perform(click())

        // skip deductions page for now
        onView(withText(R.string.next_button)).check(matches(isEnabled())).perform(click())

        onView(allOf(
                not(withText(R.string.taxes_owed_label)),
                isAssignableFrom(TextView::class.java),
                hasSibling(withText(R.string.taxes_owed_label))
        )).check(matches(isDisplayed())).check(matches(withText("4366")))
    }
}
