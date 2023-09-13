package com.duongvn.asteroidradar.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import com.duongvn.asteroidradar.databinding.ItemAsteroidBinding
import com.duongvn.asteroidradar.databinding.ItemHeaderBinding

class AsteroidAdapter(private val listener: AsteroidAdapterListener) :
    ListAdapter<AsteroidAdapter.Item, RecyclerView.ViewHolder>(diffUtilItem) {

    inner class AsteroidHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(t: Asteroid) {
            binding.also {
                it.listener = listener
                it.asteroid = t
                it.executePendingBindings()
            }
        }
    }

    inner class HeaderHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(apod: Apod) {
            binding.also {
                it.apod = apod
                it.executePendingBindings()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.HEADER -> 0
            is Item.NORMAL -> 1
        }
    }

    fun interface AsteroidAdapterListener {
        fun onItemClick(asteroid: Asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemType.values()[viewType]) {
            ItemType.HEADER -> {
                HeaderHolder(
                    ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            ItemType.NORMAL -> {
                AsteroidHolder(
                    ItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).also {
            when (it) {
                is Item.HEADER -> {
                    (holder as HeaderHolder).bind(it.apod)
                }

                is Item.NORMAL -> {
                    (holder as AsteroidHolder).bind(it.asteroid)
                }
            }
        }
    }

    companion object {

        private val diffUtilItem = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return (newItem == oldItem)
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return (newItem == oldItem)
            }
        }
    }

    sealed class Item {
        data class HEADER(val apod: Apod) : Item()
        data class NORMAL(val asteroid: Asteroid) : Item()
    }

    enum class ItemType {
        HEADER, NORMAL
    }
}