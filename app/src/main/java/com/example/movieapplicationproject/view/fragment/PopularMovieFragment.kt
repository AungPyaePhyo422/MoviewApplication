package com.example.movieapplicationproject.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.movieapplicationproject.R
import com.example.movieapplicationproject.databinding.FragmentPopularMovieBinding
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.view.fragment.adapter.AdapterOnClick
import com.example.movieapplicationproject.view.fragment.adapter.MovieAdapter
import com.example.movieapplicationproject.viewmodel.MovieMainPageViewModel
import com.example.movieapplicationproject.viewmodel.MovieRoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : Fragment() {

    private var _binding : FragmentPopularMovieBinding? = null
    private val binding : FragmentPopularMovieBinding get() = _binding!!

    private val viewModel : MovieMainPageViewModel by viewModels()
    private val viewModelRoom : MovieRoomViewModel by viewModels()

    private val  adapterPopularMovie : MovieAdapter by lazy {
        MovieAdapter(object : AdapterOnClick{
            override fun onItemClicked(item: MovieNetworkData) {

                val action = PopularMovieFragmentDirections.actionPopularMovieFragmentToDetailShowFragment(item)
                findNavController().navigate(action)

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPopularMovieBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPopularMovie.apply {
            setHasFixedSize(true)
            adapter = adapterPopularMovie
        }

        viewModel.getPopularMovie()
        viewModel.popularMovie.observe(viewLifecycleOwner){
            when(it){
                is AsyncViewResource.Error -> {
                    Toast.makeText(context, "PopularMovie Fount Error ${it.errorMessage}", Toast.LENGTH_SHORT).show()
                }
                is AsyncViewResource.Loading -> {
                    Log.d("PopularMovie", "Loading?????!!")
                }
                is AsyncViewResource.Success -> {
                    Log.d("PopularMovie", "Success?????!!")
                    adapterPopularMovie.submitList(it.value?.results)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_favourite_logo, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId) {
            R.id.favourite_movie -> {
                val action = PopularMovieFragmentDirections.actionPopularMovieFragmentToFavouriteMovieFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }

}