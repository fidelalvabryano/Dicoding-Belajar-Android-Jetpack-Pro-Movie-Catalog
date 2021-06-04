package com.fidel.movietv.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.fidel.movietv.R
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTVShow = DataDummy.generateDummyTvshows()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.titletext)).check(matches(isDisplayed()))
        onView(withId(R.id.titletext)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.durationtext)).check(matches(isDisplayed()))
        onView(withId(R.id.durationtext)).check(matches(withText(dummyMovie[0].duration)))
        onView(withId(R.id.overviewtext)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewtext)).check(matches(withText(dummyMovie[0].overview)))
        onView(withId(R.id.yeartext)).check(matches(isDisplayed()))
        onView(withId(R.id.yeartext)).check(matches(withText(dummyMovie[0].year)))
    }

    @Test
    fun loadTVShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTVShow.size))
    }

    @Test
    fun loadDetailTVShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.titletext)).check(matches(isDisplayed()))
        onView(withId(R.id.titletext)).check(matches(withText(dummyTVShow[0].title)))
        onView(withId(R.id.durationtext)).check(matches(isDisplayed()))
        onView(withId(R.id.durationtext)).check(matches(withText(dummyTVShow[0].duration)))
        onView(withId(R.id.overviewtext)).check(matches(isDisplayed()))
        onView(withId(R.id.overviewtext)).check(matches(withText(dummyTVShow[0].overview)))
        onView(withId(R.id.yeartext)).check(matches(isDisplayed()))
        onView(withId(R.id.yeartext)).check(matches(withText(dummyTVShow[0].year)))
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withText("Favorites")).perform(click())
        onView(withText("Favorite Movies")).perform(click())
        onView(withId(R.id.rv_favorite_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadFavoriteTVShows() {
        onView(withText("Favorites")).perform(click())
        onView(withText("Favorite TV Shows")).perform(click())
        onView(withId(R.id.rv_favorite_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTVShow.size))
    }

}