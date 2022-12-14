package com.example.movieapplicationproject.view.fragment

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapplicationproject.BuildConfig.BASE_IMAGE_URL
import com.example.movieapplicationproject.R
import com.example.movieapplicationproject.databinding.FragmentDetailShowBinding
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.viewmodel.MovieMainPageViewModel
import com.example.movieapplicationproject.viewmodel.MovieRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class DetailShowFragment : Fragment() {

    private var _binding : FragmentDetailShowBinding? = null
    private val binding : FragmentDetailShowBinding get() = _binding!!

    private val args : DetailShowFragmentArgs by navArgs()

    private val viewModeRoom : MovieRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var entity = MovieDatabaseEntity(0, args.dd.id!!, args.dd.title ?: args.dd.original_name!!, args.dd.overview!!, args.dd.posterPath!!, args.dd.backdropPath!!, args.dd.voteAverage.toString()!!)

        Glide.with(binding.root).load(BASE_IMAGE_URL+args.dd.posterPath).into(binding.ivPosterDetail)
        Glide.with(binding.root).load(BASE_IMAGE_URL+args.dd.backdropPath).into(binding.ivPosterDetail1)
        binding.apply {
            tvTitle.text = args.dd.originalTitle ?: args.dd.original_name
            tvOverview.text = args.dd.overview
            tvRating.text = args.dd.voteAverage.toString()

            viewModeRoom.readAllData.observe(viewLifecycleOwner){
                var favicon = false

                it.forEach{ data ->
                    Log.d("DetailID", "${args.dd.original_name ?: args.dd.originalTitle} and ${data.movieTitle}")

                    when{
                        (args.dd.original_name ?: args.dd.originalTitle) == data.movieTitle -> {
                            entity = data
                            Log.d("DetailID", "sucess")
                            favicon = true
                        }
                    }
                }

                if (favicon){
                    floatingActionButton.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.black))
                }
                else{
                    floatingActionButton.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.white))
                }

                floatingActionButton.setOnClickListener{
                    if (favicon){
                        viewModeRoom.deleteMovie(entity)
                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                        favicon = false
                    }
                    else{
                        viewModeRoom.addMovie(entity)
                        Toast.makeText(context, "Successfully Added ", Toast.LENGTH_SHORT).show()
                        favicon = true

                    }
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        
        _binding = null
    }
}