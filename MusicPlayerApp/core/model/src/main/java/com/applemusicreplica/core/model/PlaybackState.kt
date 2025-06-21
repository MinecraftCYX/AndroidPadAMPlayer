package com.applemusicreplica.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 播放状态枚举
 */
enum class PlayState {
    IDLE,       // 空闲
    PLAYING,    // 正在播放
    PAUSED,     // 暂停
    STOPPED,    // 停止
    BUFFERING,  // 缓冲中
    ERROR       // 错误
}

/**
 * 重复模式枚举
 */
enum class RepeatMode {
    OFF,        // 不重复
    ALL,        // 重复所有
    ONE         // 单曲循环
}

/**
 * 播放状态数据模型
 */
@Parcelize
data class PlaybackState(
    val currentSong: Song? = null,
    val playState: PlayState = PlayState.IDLE,
    val position: Long = 0, // 当前播放位置，毫秒
    val duration: Long = 0, // 歌曲总时长，毫秒
    val isShuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val playbackSpeed: Float = 1.0f,
    val queue: List<Song> = emptyList(),
    val currentIndex: Int = -1,
    val errorMessage: String? = null
) : Parcelable {
    
    /**
     * 获取播放进度百分比 (0.0 - 1.0)
     */
    val progress: Float
        get() = if (duration > 0) (position.toFloat() / duration.toFloat()).coerceIn(0f, 1f) else 0f
    
    /**
     * 获取格式化的当前位置
     */
    val formattedPosition: String
        get() {
            val seconds = (position / 1000).toInt()
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return String.format("%d:%02d", minutes, remainingSeconds)
        }
    
    /**
     * 获取格式化的总时长
     */
    val formattedDuration: String
        get() {
            val seconds = (duration / 1000).toInt()
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return String.format("%d:%02d", minutes, remainingSeconds)
        }
    
    /**
     * 检查是否正在播放
     */
    val isPlaying: Boolean
        get() = playState == PlayState.PLAYING
    
    /**
     * 检查是否有下一首
     */
    val hasNext: Boolean
        get() = when {
            queue.isEmpty() -> false
            isShuffleEnabled -> true
            repeatMode == RepeatMode.ALL -> true
            repeatMode == RepeatMode.ONE -> true
            else -> currentIndex < queue.size - 1
        }
    
    /**
     * 检查是否有上一首
     */
    val hasPrevious: Boolean
        get() = when {
            queue.isEmpty() -> false
            isShuffleEnabled -> true
            repeatMode == RepeatMode.ALL -> true
            repeatMode == RepeatMode.ONE -> true
            else -> currentIndex > 0
        }
}
