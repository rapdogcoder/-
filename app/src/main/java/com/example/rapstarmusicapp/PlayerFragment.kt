package com.example.rapstarmusicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.rapstarmusicapp.MusicAdapter
import com.example.rapstarmusicapp.PlayerModel
import com.example.rapstarmusicapp.R
import com.example.rapstarmusicapp.databinding.FragmentPlayerBinding
import com.example.rapstarmusicapp.service.MusicModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import java.util.*
import java.util.concurrent.TimeUnit

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var _binding: FragmentPlayerBinding? = null
    private lateinit var adapter: MusicAdapter
    private var model: PlayerModel = PlayerModel()

    private val binding get() = requireNotNull(_binding)

    private var player: ExoPlayer ? = null

    private val updateSeekRunnable = Runnable {
        updateSeek()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)

        @Suppress("UnnecessaryVariable")
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlayView()
        initPlayListButton()
        initPlayControlButtons()
        initSeekBar()
        initRecyclerView()
        getVideoListFromServer()
    }

    private fun getVideoListFromServer() {
        TODO("Not yet implemented")
    }


    @Suppress("ClickableViewAccessibility")
    private fun initSeekBar() {
        binding.playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                if (seekbar != null) {
                    player?.seekTo(seekbar.progress * 1000L)
                }
            }
        })
    }

    private fun initPlayControlButtons() {
        binding.playControlImageView.setOnClickListener{
            val player = this.player ?: return@setOnClickListener

            if(player.isPlaying){
                player.pause()
            } else {
                player.play()
            }
        }
        binding.skipNextImageView. setOnClickListener{
            val nextMusic = model.nextMusic() ?: return@setOnClickListener
            playMusic(nextMusic)
        }
        binding.skipPrevImageView.setOnClickListener{
            val prevMusic = model.prevMusic() ?: return@setOnClickListener
            playMusic(prevMusic)
        }
    }

    private fun playMusic(nextMusic: MusicModel) {

    }


    private fun initPlayView() {
        context?.let{
            player = ExoPlayer.Builder(it).build()
        }
        binding.playerView.player = player

        player?.addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                if (isPlaying){
                    binding.playControlImageView.setImageResource(R.drawable.ic_baseline_pause_24)
                } else {
                    binding.playControlImageView.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)

                val newIndex: String = mediaItem?.mediaId ?: return
                model.currentPosition = newIndex.toInt()
                adapter.submitList(model.getAdapterModels())

                binding.playListRecyclerView.scrollToPosition(model.currentPosition)

                updatePlayerView(model.currentMusicModel())
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)

                updateSeek()
            }
        })
    }

    private fun updatePlayerView(currentMusicModel: MusicModel?) {

    }

    private fun updateSeek() {
        val player = this.player ?: return
        val duration = if (player.duration >= 0) player.duration else 0
        val position = player.currentPosition

        updateSeekUi(duration, position)

        val state = player.playbackState

        view?.removeCallbacks(updateSeekRunnable)

        if(state != Player.STATE_IDLE && state != Player.STATE_ENDED){
            view?.postDelayed(updateSeekRunnable, 1000)
        }
    }

    private fun updateSeekUi(duration: Long, position: Long) {
        binding.playerSeekBar.max = (duration /1000).toInt()
        binding.playListSeekBar.progress = (position / 1000).toInt()

        binding.playerSeekBar.max = (duration/1000).toInt()
        binding.playerSeekBar.progress = (duration / 1000).toInt()

        binding.playTimeTextView.text = String.format(
            "%02d:%02d",
            TimeUnit.MINUTES.convert(position, TimeUnit.MILLISECONDS),
            (position / 1000) % 60
        )
        binding.totalTimeTextView.text = String.format(
            "%02d:%02d",
            TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS),
            (duration / 1000) % 60
        )
    }

    private fun initRecyclerView() {
        binding.playListImageView.setOnClickListener{

            if(model.currentPosition == -1) return@setOnClickListener

            binding.playListGroup.isVisible = binding.playerViewGroup.isVisible.also {
                binding.playerViewGroup.isVisible = binding.playListGroup.isVisible
            }
        }
    }

    private fun initPlayListButton() {
        binding.playListImageView.setOnClickListener{
            if(model.currentPosition == 1) return@setOnClickListener

            binding.playListGroup.isVisible = binding.playerViewGroup.isVisible.also{

            }
        }
    }

}