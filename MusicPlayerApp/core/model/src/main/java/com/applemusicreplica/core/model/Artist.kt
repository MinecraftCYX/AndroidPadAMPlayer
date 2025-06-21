package com.applemusicreplica.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 艺术家数据模型
 */
@Parcelize
data class Artist(
    val id: Long = 0,
    val name: String = "",
    val albumCount: Int = 0,
    val songCount: Int = 0,
    val albumArtPath: String? = null, // 使用第一张专辑的封面
    val firstAlbumId: Long = 0
) : Parcelable {
    
    /**
     * 获取显示用的艺术家名称
     */
    val displayName: String
        get() = if (name.isBlank()) "未知艺术家" else name
    
    /**
     * 获取格式化的专辑数量
     */
    val formattedAlbumCount: String
        get() = "${albumCount} 张专辑"
    
    /**
     * 获取格式化的歌曲数量
     */
    val formattedSongCount: String
        get() = "${songCount} 首歌曲"
    
    /**
     * 获取完整的统计信息
     */
    val fullStats: String
        get() = "${formattedAlbumCount} • ${formattedSongCount}"
}

/**
 * 播放列表数据模型
 */
@Parcelize
data class Playlist(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val songCount: Int = 0,
    val coverImagePath: String? = null,
    val createdDate: Long = 0,
    val modifiedDate: Long = 0,
    val totalDuration: Long = 0,
    val isSystemPlaylist: Boolean = false // 标识是否为系统播放列表（如最近添加、最爱等）
) : Parcelable {
    
    /**
     * 获取显示用的播放列表名称
     */
    val displayName: String
        get() = if (name.isBlank()) "未命名播放列表" else name
    
    /**
     * 获取格式化的歌曲数量
     */
    val formattedSongCount: String
        get() = "${songCount} 首歌曲"
    
    /**
     * 获取格式化的总时长
     */
    val formattedDuration: String
        get() {
            val seconds = (totalDuration / 1000).toInt()
            val minutes = seconds / 60
            val hours = minutes / 60
            
            return when {
                hours > 0 -> {
                    val remainingMinutes = minutes % 60
                    String.format("%d 小时 %d 分钟", hours, remainingMinutes)
                }
                minutes > 0 -> String.format("%d 分钟", minutes)
                else -> "少于 1 分钟"
            }
        }
}
