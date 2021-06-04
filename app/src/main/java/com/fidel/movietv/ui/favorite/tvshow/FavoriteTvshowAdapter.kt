package com.fidel.movietv.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.databinding.ItemsTvshowBinding
import com.fidel.movietv.ui.detail.DetailFilmActivity

class FavoriteTvshowAdapter : PagedListAdapter<TVShowEntity, FavoriteTvshowAdapter.TvshowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVShowEntity>() {
            override fun areItemsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem.filmId == newItem.filmId
            }
            override fun areContentsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val listFavoriteTvshows = ArrayList<TVShowEntity>()

    fun setFavoriteTvshows(tvShows: List<TVShowEntity>?) {
        if (tvShows == null) return
        this.listFavoriteTvshows.clear()
        this.listFavoriteTvshows.addAll(tvShows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvshowViewHolder {
        val itemsFavoriteTVShowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvshowViewHolder(itemsFavoriteTVShowBinding)
    }

    override fun onBindViewHolder(holder: TvshowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    override fun getItemCount(): Int = listFavoriteTvshows.size

    fun getSwipedData(swipedPosition: Int): TVShowEntity? = getItem(swipedPosition)

    inner class TvshowViewHolder(private val binding: ItemsTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TVShowEntity) {
            with(binding) {
                titletext.text = tvShow.title
                yeartext.text = tvShow.year
                Glide.with(itemView.context)
                    .load(tvShow.image)
                    .into(movieimage)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_FILM, tvShow.filmId)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }
}