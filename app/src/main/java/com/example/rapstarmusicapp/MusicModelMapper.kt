package com.example.rapstarmusicapp

import com.example.rapstarmusicapp.service.MusicDto
import com.example.rapstarmusicapp.service.MusicEntity
import com.example.rapstarmusicapp.service.MusicModel

fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(id = id, track,streamUrl,artist,coverUrl)
fun MusicDto.mapper() : PlayerModel =
    PlayerModel(
        playMusicList = musics.mapIndexed{
            index, musicEntity ->
            musicEntity.mapper(index.toLong())
        }
    )