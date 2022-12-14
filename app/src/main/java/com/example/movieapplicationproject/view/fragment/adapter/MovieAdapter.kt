package com.example.movieapplicationproject.view.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplicationproject.BuildConfig.BASE_IMAGE_URL
import com.example.movieapplicationproject.databinding.ItemPopularMovieBinding
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData

class MovieAdapter(private val event : AdapterOnClick) :
    ListAdapter<MovieNetworkData, MovieAdapter.MyViewHolder>(DiffCallBack) {


    class MyViewHolder(private val binding: ItemPopularMovieBinding, private val item: AdapterOnClick) : RecyclerView.ViewHolder(binding.root) {

        private var currentMovieNetworkData : MovieNetworkData? = null

        init {
            binding.root.setOnClickListener{
                currentMovieNetworkData?.apply {
                    item.onItemClicked(this)
                }
            }
        }

        fun bind(movieNetworkData : MovieNetworkData){
            currentMovieNetworkData = movieNetworkData
            Glide.with(binding.root).load(BASE_IMAGE_URL + movieNetworkData.posterPath).into(binding.ivPosterItem)
            binding.tvMovieName.text = movieNetworkData.originalTitle ?: movieNetworkData.original_name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view, event)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

interface AdapterOnClick {

    fun onItemClicked(item : MovieNetworkData)
}

object DiffCallBack : DiffUtil.ItemCallback<MovieNetworkData>() {
    override fun areItemsTheSame(oldItem: MovieNetworkData, newItem: MovieNetworkData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MovieNetworkData, newItem: MovieNetworkData): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}