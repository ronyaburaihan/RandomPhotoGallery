package com.englesoft.randomphotogallery.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.englesoft.randomphotogallery.R
import com.englesoft.randomphotogallery.adapters.ImageListAdapter
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.englesoft.randomphotogallery.databinding.FragmentListBinding
import com.englesoft.randomphotogallery.utils.NetworkResult
import com.englesoft.randomphotogallery.viewmodels.MainViewModel


class ListFragment : Fragment(), ImageListAdapter.ImageItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageListAdapter = ImageListAdapter(this)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        binding.rvImages.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = imageListAdapter
        }

        mainViewModel.getImageList()

        mainViewModel.imageListLiveData.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                binding.progressCircular.isVisible = false
                imageListAdapter.setData(it)
            }
        }

        mainViewModel.imageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    //Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    binding.progressCircular.isVisible = true
                }
                is NetworkResult.Success -> {
                    binding.progressCircular.isVisible = false
                    imageListAdapter.setData(it.data!!)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(imageListResponse: ImageListResponse) {
        val action =
            ListFragmentDirections.actionListFragmentToDetailsFragment(imageListResponse.image.regular)
        findNavController().navigate(action)
    }
}