package com.duongvn.asteroidradar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.duongvn.asteroidradar.R
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import com.duongvn.asteroidradar.databinding.FragmentHomeBinding
import com.duongvn.asteroidradar.ui.home.adapter.AsteroidAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.FACTORY
    }
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val asteroidAdapter = AsteroidAdapter {
        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it).also { action ->
            findNavController().navigate(action)
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_home_fragment, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.show_all -> {
                    viewModel.setTypeShow(HomeViewModel.TypeShow.ALL)
                }

                R.id.today -> {
                    viewModel.setTypeShow(HomeViewModel.TypeShow.TODAY)
                }

                R.id.now_to_seven_date -> {
                    viewModel.setTypeShow(HomeViewModel.TypeShow.WEEK)
                }
            }

            return true
        }
    }

    private val menuHost: MenuHost by lazy {
        requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuHost.addMenuProvider(menuProvider)

        binding.apply {
            rcAsteroid.also {
                it.adapter = asteroidAdapter
                it.layoutManager = LinearLayoutManager(requireActivity())
            }
        }

        initObserver()

        viewModel.fetchAsteroi()
        viewModel.fetchImageOfDay()
    }

    private fun initObserver() {
        viewModel.apply {
            asteroiList.observe(viewLifecycleOwner) { asteroids ->
                val list = listOf(AsteroidAdapter.Item.HEADER(imageResult.value ?: Apod()))
                asteroids.map { AsteroidAdapter.Item.NORMAL(it) }.also {
                    asteroidAdapter.submitList(list + it)
                }
            }

            imageResult.observe(viewLifecycleOwner) { apod ->
                val header = listOf(AsteroidAdapter.Item.HEADER(apod))
                val normal = asteroiList.value?.map { AsteroidAdapter.Item.NORMAL(it) } ?: emptyList()
                asteroidAdapter.submitList(header + normal)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        menuHost.removeMenuProvider(menuProvider)
    }

    override fun onDestroy() {
        super.onDestroy()
        menuHost.removeMenuProvider(menuProvider)
    }
}