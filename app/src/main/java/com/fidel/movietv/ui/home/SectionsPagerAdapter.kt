package com.fidel.movietv.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fidel.movietv.R
import com.fidel.movietv.ui.favorite.FavoriteFragment
import com.fidel.movietv.ui.movies.MoviesFragment
import com.fidel.movietv.ui.tvshows.TvshowsFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tvshows, R.string.favorites)
    }

    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> MoviesFragment()
                1 -> TvshowsFragment()
                2 -> FavoriteFragment()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLES[position])

}