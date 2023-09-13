package com.duongvn.asteroidradar.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duongvn.asteroidradar.databinding.ItemDetailBinding
import com.duongvn.asteroidradar.databinding.ItemDetailHeaderBinding
import com.duongvn.asteroidradar.ui.detail.dto.DetailDto

class DetailAdapter(private val listener: DetailAdapterListener) :
    ListAdapter<DetailAdapter.Item, RecyclerView.ViewHolder>(diffUtilItem) {

    inner class DetailHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(t: DetailDto) {
            binding.also {
                it.dto = t
                it.listener = listener
                it.executePendingBindings()
            }
        }
    }

    inner class HeaderHolder(private val binding: ItemDetailHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(isPotentiallyHazardous: Boolean) {
            binding.also {
                it.isPotentiallyHazardous = isPotentiallyHazardous
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

    fun interface DetailAdapterListener {
        fun onItemClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ItemType.values()[viewType]) {
            ItemType.HEADER -> {
                HeaderHolder(
                    ItemDetailHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ItemType.NORMAL -> {
                DetailHolder(
                    ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).also {
            when (it) {
                is Item.HEADER -> {
                    (holder as HeaderHolder).bind(it.isPotentiallyHazardous)
                }

                is Item.NORMAL -> {
                    (holder as DetailHolder).bind(it.detailDto)
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
        data class HEADER(val isPotentiallyHazardous: Boolean) : Item()
        data class NORMAL(val detailDto: DetailDto) : Item()
    }

    enum class ItemType {
        HEADER, NORMAL
    }
}