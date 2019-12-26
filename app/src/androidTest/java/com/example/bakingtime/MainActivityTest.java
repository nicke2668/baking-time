package com.example.bakingtime;

import com.example.bakingtime.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

	private static ViewInteraction matchActionBarTitle(CharSequence title) {
		return onView(isAssignableFrom(ActionBarOverlayLayout.class)).check(matches(withActionBarTitle(is(title))));
	}

	private static Matcher<Object> withActionBarTitle(final Matcher<CharSequence> textMatcher) {
		return new BoundedMatcher<Object, ActionBarOverlayLayout>(ActionBarOverlayLayout.class) {
			@Override
			public void describeTo(Description description) {
				description.appendText("with toolbar title: ");
				textMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(ActionBarOverlayLayout toolbar) {
				return textMatcher.matches(toolbar.getTitle());
			}
		};
	}

	@Rule
	final public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void clickedRecipeOpensCorrectDetailScreen() {
		onView(withId(R.id.recipesRecyclerView))
				.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
		matchActionBarTitle("Nutella Pie").check(matches(isDisplayed()));
	}
}
