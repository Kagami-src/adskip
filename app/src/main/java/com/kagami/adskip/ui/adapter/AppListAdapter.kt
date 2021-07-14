package com.kagami.adskip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kagami.adskip.databinding.ItemApplistBinding
import com.kagami.adskip.db.DBAdItem

class AppListAdapter : ListAdapter<DBAdItem, AppListAdapter.ViewHolder>(AppDiffCallback()){
    class ViewHolder(val binding:ItemApplistBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:DBAdItem){
            binding.nameText.text=data.appName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemApplistBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class AppDiffCallback : DiffUtil.ItemCallback<DBAdItem>() {

    override fun areItemsTheSame(oldItem: DBAdItem, newItem: DBAdItem): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: DBAdItem, newItem: DBAdItem): Boolean {
        return oldItem.appName == newItem.appName
                && oldItem.packageName == newItem.packageName
    }


}