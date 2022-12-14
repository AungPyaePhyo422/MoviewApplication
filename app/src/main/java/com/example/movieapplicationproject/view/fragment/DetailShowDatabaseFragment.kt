package com.example.movieapplicationproject.view.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapplicationproject.BuildConfig
import com.example.movieapplicationproject.R
import com.example.movieapplicationproject.databinding.FragmentDetailShowBinding
import com.example.movieapplicationproject.databinding.FragmentDetailShowDatabaseBinding
import com.example.movieapplicationproject.viewmodel.MovieRoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailShowDatabaseFragment : Fragment() {

    private var _binding : FragmentDetailShowDatabaseBinding? = null
    private val binding : FragmentDetailShowDatabaseBinding get() = _binding!!

    private val args : DetailShowDatabaseFragmentArgs by navArgs()

    private val viewModeRoom : MovieRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailShowDatabaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.root).load(BuildConfig.BASE_IMAGE_URL +args.dd.posterPath).into(binding.ivPosterDetail)
        Glide.with(binding.root).load(BuildConfig.BASE_IMAGE_URL +args.dd.posterPath).into(binding.ivPosterDetail1)
        binding.apply {
            tvOverview.text = args.dd.movieOverView
            tvTitle.text = args.dd.movieTitle
            tvRating.text = args.dd.rating
        }

        var favicon = true

        binding.apply{
            floatingActionButton.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.black))

            floatingActionButton.setOnClickListener{
                if (favicon){
                    viewModeRoom.deleteMovie(args.dd)
                    floatingActionButton.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.white))
                    favicon = false
                }
                else{
                    viewModeRoom.addMovie(args.dd)
                    floatingActionButton.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.black))
                    favicon = true

                }
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}