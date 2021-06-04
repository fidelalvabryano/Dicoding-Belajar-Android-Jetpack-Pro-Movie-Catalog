package com.fidel.movietv.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fidel.movietv.R
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.databinding.FragmentFavoriteTvshowBinding
import com.fidel.movietv.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteTvshowFragment : Fragment(){

    private var _fragmentFavoriteTvshowBinding: FragmentFavoriteTvshowBinding? = null
    private val binding get() =_fragmentFavoriteTvshowBinding

    private lateinit var viewModel: FavoriteTvshowViewModel
    private lateinit var adapter: FavoriteTvshowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteTvshowBinding = FragmentFavoriteTvshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTvshows)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteTvshowViewModel::class.java]

            adapter = FavoriteTvshowAdapter()
            binding?.progressBar?.visibility =View.VISIBLE
            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { tvShows ->
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(tvShows)
            })

            binding?.rvFavoriteTvshows?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavoriteTvshows?.setHasFixedSize(true)
            binding?.rvFavoriteTvshows?.adapter = adapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = adapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavoriteTVShow(it) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    tvShowEntity?.let { viewModel.setFavoriteTVShow(it) }
                }
                snackbar.show()
            }
        }
    })


}