package com.example.movieapplicationproject.view.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplicationproject.BuildConfig
import com.example.movieapplicationproject.BuildConfig.BASE_IMAGE_URL
import com.example.movieapplicationproject.databinding.ItemDatabaseListBinding
import com.example.movieapplicationproject.databinding.ItemPopularMovieBinding
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.model.MovieRepository
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.viewmodel.MovieMainPageViewModel
import com.example.movieapplicationproject.viewmodel.MovieRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MovieRoomAdapter(private val event : AdapterRoomItemClick) : androidx.recyclerview.widget.ListAdapter<MovieDatabaseEntity, MovieRoomAdapter.MyViewModel>(DiffRoomCallBack) {


    class MyViewModel(private val binding : ItemDatabaseListBinding , private val itemClick: AdapterRoomItemClick) : RecyclerView.ViewHolder(binding.root) {

        private var currentMovieNetworkData : MovieDatabaseEntity? = null

        init {
            binding.root.setOnClickListener{
                currentMovieNetworkData?.apply {
                    itemClick.onItemClicked(this)
                }
            }
        }

        fun bind(entity: MovieDatabaseEntity){
            currentMovieNetworkData = entity
            Glide.with(binding.root).load(BASE_IMAGE_URL+entity.posterPath).into(binding.ivPosterItem)
            binding.tvMovieNameItem.text = entity.movieTitle
            binding.tvRating.text = entity.rating
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view = ItemDatabaseListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewModel(view, event)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

interface AdapterRoomItemClick{

    fun onItemClicked(item : MovieDatabaseEntity)

}

object DiffRoomCallBack : DiffUtil.ItemCallback<MovieDatabaseEntity>() {
    override fun areItemsTheSame(
        oldItem: MovieDatabaseEntity,
        newItem: MovieDatabaseEntity
    ): Boolean {
       return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MovieDatabaseEntity,
        newItem: MovieDatabaseEntity
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}
