package com.rteslenko.android.moviesgallery.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rteslenko.android.moviesgallery.R
import com.rteslenko.android.moviesgallery.databinding.FragmentMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MoviesListViewModel by viewModels()

    private val adapter by lazy {
        MoviesListAdapter(requireContext()) { movie, view ->
            val action = MoviesListFragmentDirections.actionMoviesListToMovieDetails(movie.id)
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(this.javaClass.simpleName, "Navigation error: ${e.message}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(view)
        observeData()
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupRecycler(view: View) {
        with(view.findViewById<RecyclerView>(R.id.recycler)) {
            val orientation = RecyclerView.VERTICAL
            layoutManager = LinearLayoutManager(requireContext(), orientation, false)
            adapter = this@MoviesListFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), orientation))
        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.noMovies.visibility = if (isEmpty) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        viewModel.isProgress.observe(viewLifecycleOwner) { isProgress ->
            binding.swipeRefresh.isRefreshing = isProgress
        }
        viewModel.isNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                onNetworkError()
            }
        }
    }

    private fun onNetworkError() {
        Snackbar.make(binding.root, R.string.network_error, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.dismiss) {
                dismiss()
            }
            show()
        }
        viewModel.notifyNetworkErrorShown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}