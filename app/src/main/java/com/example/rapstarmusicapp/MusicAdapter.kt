package com.example.rapstarmusicapp

import android.widget.ListAdapter
import com.example.rapstarmusicapp.service.MusicModel

class MusicAdapter(private val callback: (MusicModel) -> Unit): ListAdapter<MusicModel, MusicAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val itemMusicBinding:  ItemMusicB)
}