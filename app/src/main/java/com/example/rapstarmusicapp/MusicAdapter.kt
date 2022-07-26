package com.example.rapstarmusicapp

import android.content.ClipData
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rapstarmusicapp.MusicAdapter.ViewHolder
import com.example.rapstarmusicapp.databinding.ItemMusicBinding
import com.example.rapstarmusicapp.service.MusicModel
import com.google.android.exoplayer2.ui.SubtitleView

class MusicAdapter(private val callback: (MusicModel) -> Unit): androidx.recyclerview.widget.ListAdapter<MusicModel, ViewHolder>(diffUtil) {

    inner class ViewHolder(private val itemMusicBinding: ItemMusicBinding):RecyclerView.ViewHolder(itemMusicBinding.root){
        fun bind(music: MusicModel){
            itemMusicBinding.itemArtistTextView.text = music.artist
            itemMusicBinding.itemTrackTextView.text = music.track

            Glide.with(itemMusicBinding.itemCoverImageView.context)
                .load(music.coverUrl)
                .into(itemMusicBinding.itemCoverImageView)
            if(music.isPlaying){
                itemView.setBackgroundColor(Color.GRAY)
            }else{
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }
            itemView.setOnClickListener{
                callback(music)
            }


        }
    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int) : ViewHolder{
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}


