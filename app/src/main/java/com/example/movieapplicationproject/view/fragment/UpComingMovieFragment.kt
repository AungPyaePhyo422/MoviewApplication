package com.example.movieapplicationproject.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.movieapplicationproject.R
import com.example.movieapplicationproject.databinding.FragmentUpComingMovieBinding
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.view.fragment.adapter.AdapterOnClick
import com.example.movieapplicationproject.view.fragment.adapter.MovieAdapter
import com.example.movieapplicationproject.viewmodel.MovieMainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class UpComingMovieFragment : Fragment() {

    private var _binding : FragmentUpComingMovieBinding? = null
    private val binding : FragmentUpComingMovieBinding get() = _binding!!

    private val  adapterUpComingMovie : MovieAdapter by lazy {
        MovieAdapter(object : AdapterOnClick {
            override fun onItemClicked(item: MovieNetworkData) {
                Toast.makeText(context, " ${item.original_name ?: item.originalTitle} Clicked", Toast.LENGTH_SHORT).show()
                val action = UpComingMovieFragmentDirections.actionUpComingMovieFragmentToDetailShowFragment(item)
                findNavController().navigate(action)
            }
        })
    }

    private val viewModel : MovieMainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpComingMovieBinding.inflate(layoutInflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUpComingMovie.apply {
            setHasFixedSize(true)
            adapter = adapterUpComingMovie
        }

        viewModel.getUpcomingMovie()
        viewModel.upcomingMovie.observe(viewLifecycleOwner){
            when(it){
                is AsyncViewResource.Error -> {
                    Toast.makeText(context, "UpComingMovie Fount Error ${it.errorMessage}", Toast.LENGTH_SHORT).show()
                }
                is AsyncViewResource.Loading -> {
                    Log.d("UpComingMovie", "Loading?????!!")
                    binding.rvUpComingMovie.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE


                }
                is AsyncViewResource.Success -> {
                    Log.d("UpComingMovie", "Success?????!!")
                    lifecycleScope.launch{
                        delay(2000)
                    }
                    binding.pbLoading.visibility = View.GONE
                    binding.rvUpComingMovie.visibility = View.VISIBLE
                    adapterUpComingMovie.submitList(it.value?.results)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favourite_logo, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId) {
            R.id.favourite_movie -> {
                val action = UpComingMovieFragmentDirections.actionUpComingMovieFragmentToFavouriteMovieFragment()
                findNavController().navigate(action)
            }
        }
        return true

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}