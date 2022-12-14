package com.example.movieapplicationproject.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapplicationproject.R
import com.example.movieapplicationproject.databinding.FragmentFavouriteMovieBinding
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.view.fragment.adapter.AdapterOnClick
import com.example.movieapplicationproject.view.fragment.adapter.AdapterRoomItemClick
import com.example.movieapplicationproject.view.fragment.adapter.MovieAdapter
import com.example.movieapplicationproject.view.fragment.adapter.MovieRoomAdapter
import com.example.movieapplicationproject.viewmodel.MovieMainPageViewModel
import com.example.movieapplicationproject.viewmodel.MovieRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FavouriteMovieFragment : Fragment() {

    private var _binding : FragmentFavouriteMovieBinding? = null
    private val binding : FragmentFavouriteMovieBinding get() = _binding!!

    private val viewRoomModel : MovieRoomViewModel by viewModels()

    private val  adapterPopularMovie : MovieRoomAdapter by lazy {
        MovieRoomAdapter(object : AdapterRoomItemClick {
            override fun onItemClicked(item: MovieDatabaseEntity) {
                val action = FavouriteMovieFragmentDirections.actionFavouriteMovieFragmentToDetailShowDatabaseFragment(item)
                findNavController().navigate(action)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteMovieBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPopularMovie.apply {
            setHasFixedSize(true)
            adapter = adapterPopularMovie
        }

        viewRoomModel.readAllData.observe(viewLifecycleOwner){ savedData ->
            adapterPopularMovie.submitList(savedData)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_favourite, menu)
        val item = menu.findItem(R.id.favourite_movie2)
        val tempArray = ArrayList<MovieDatabaseEntity>()
        val adapter : ArrayAdapter<MovieDatabaseEntity> = ArrayAdapter(
            requireContext().applicationContext, android.R.layout.simple_list_item_1, tempArray
        )

        val search = item!!.actionView as SearchView
        search.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                search.clearFocus()
                search.setQuery("", false)
                search.onActionViewCollapsed()
                viewRoomModel.readAllData.observe(viewLifecycleOwner){ savedData ->

                    val searchText = p0!!.lowercase(Locale.getDefault())
                    savedData.forEach{
                        if (it.movieTitle.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArray.add(it)
                            adapter.filter.filter(p0)
                        }
                    }


                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(context, "$p0", Toast.LENGTH_SHORT).show()
                adapter.filter.filter(p0)
                adapterPopularMovie.submitList(tempArray)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)



        when(item.itemId) {
            R.id.favourite_movie -> {
                item.itemId
            }
            R.id.favourite_movie2 -> {

            }
            else -> {
                findNavController().navigateUp()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}