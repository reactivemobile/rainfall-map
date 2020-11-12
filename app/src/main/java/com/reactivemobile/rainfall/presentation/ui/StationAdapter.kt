package com.reactivemobile.rainfall.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.domain.model.Station

class StationAdapter : ListAdapter<Station, StationAdapter.StationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_station_item, null)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.update(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Station> = object : DiffUtil.ItemCallback<Station>() {
            override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem == newItem
            }
        }
    }

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)

        fun update(item: Station) {
            name.text = item.name
        }
    }
}

