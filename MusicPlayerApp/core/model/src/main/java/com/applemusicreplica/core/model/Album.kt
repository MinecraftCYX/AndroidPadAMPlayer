package com.applemusicreplica.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 专辑数据模型
 */
@Parcelize
data class Album(
    val id: Long = 0,
    val name: String = "",
    val artist: String = "",
    val artistId: Long = 0,
    val songCount: Int = 0,
    val year: Int = 0,
    val albumArtPath: String? = null,
    val firstSongId: Long = 0,
    val totalDuration: Long = 0, // 专辑总时长，毫秒
    val dateAdded: Long = 0
) : Parcelable {
    
    /**
     * 获取显示用的专辑名称
     */
    val displayName: String
        get() = if (name.isBlank()) "未知专辑" else name
    
    /**
     * 获取显示用的艺术家名称
     */
    val displayArtist: String
        get() = if (artist.isBlank()) "未知艺术家" else artist
    
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
    
    /**
     * 获取年份信息（如果有效）
     */
    val yearInfo: String?
        get() = if (year > 0) year.toString() else null
}
