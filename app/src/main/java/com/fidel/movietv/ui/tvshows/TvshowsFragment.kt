package com.fidel.movietv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fidel.movietv.databinding.FragmentTvshowsBinding
import com.fidel.movietv.ui.movies.MoviesViewModel
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.viewmodel.ViewModelFactory
import com.fidel.movietv.vo.Status

class TvshowsFragment : Fragment() {

    private lateinit var fragmentTvshowBinding: FragmentTvshowsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentTvshowBinding = FragmentTvshowsBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return fragmentTvshowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvshowsViewModel::class.java]

            val tvshowsAdapter = TvshowsAdapter()
            viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    when(tvShows.status) {
                        Status.LOADING -> fragmentTvshowBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentTvshowBinding.progressBar.visibility = View.GONE
                            tvshowsAdapter.setFilms(tvShows.data)
                            tvshowsAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            fragmentTvshowBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentTvshowBinding.rvTvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvshowsAdapter

            }
        }
    }
}