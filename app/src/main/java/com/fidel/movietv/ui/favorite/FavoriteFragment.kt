package com.fidel.movietv.ui.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fidel.movietv.R
import com.fidel.movietv.databinding.FragmentFavoriteBinding
import com.fidel.movietv.databinding.FragmentMoviesBinding

class FavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return fragmentFavoriteBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let { setUpViewPager(it) }

    }

    private fun setUpViewPager(context: Context) {
        val favoriteSectionsPagerAdapter = FavoriteSectionsPagerAdapter(context, childFragmentManager)
        fragmentFavoriteBinding.favoriteViewPager.adapter = favoriteSectionsPagerAdapter
        fragmentFavoriteBinding.favoriteTabs.setupWithViewPager(fragmentFavoriteBinding.favoriteViewPager)
    }


}