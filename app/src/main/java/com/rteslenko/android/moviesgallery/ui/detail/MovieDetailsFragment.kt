package com.rteslenko.android.moviesgallery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rteslenko.android.moviesgallery.databinding.FragmentMovieDetailsBinding
import com.rteslenko.android.moviesgallery.utils.ImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val imageLoader by lazy { ImageLoader(requireContext()) }

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getMovieDetails(args.id)
    }

    private fun observeData() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            imageLoader.loadPoster(movie.posterPath, binding.image)
            binding.title.text = movie.title
            binding.releaseDate.text = movie.releaseDate
            binding.description.text = movie.overview
        }

        viewModel.isProgress.observe(viewLifecycleOwner) { isProgress ->
            binding.progressBar.visibility = if (isProgress) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            binding.error.visibility = if (isError) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}