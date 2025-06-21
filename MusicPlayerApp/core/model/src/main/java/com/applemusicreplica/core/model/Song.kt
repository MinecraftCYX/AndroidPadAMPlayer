package com.applemusicreplica.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 歌曲数据模型
 */
@Parcelize
data class Song(
    val id: Long = 0,
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val albumId: Long = 0,
    val duration: Long = 0, // 毫秒
    val filePath: String = "",
    val albumArtPath: String? = null,
    val trackNumber: Int = 0,
    val year: Int = 0,
    val genre: String = "",
    val size: Long = 0,
    val mimeType: String = "",
    val dateAdded: Long = 0,
    val dateModified: Long = 0,
    val isLoved: Boolean = false,
    val playCount: Int = 0,
    val lastPlayTime: Long = 0
) : Parcelable {
    
    /**
     * 获取显示用的艺术家名称
     */
    val displayArtist: String
        get() = if (artist.isBlank()) "未知艺术家" else artist
    
    /**
     * 获取显示用的专辑名称
     */
    val displayAlbum: String
        get() = if (album.isBlank()) "未知专辑" else album
    
    /**
     * 获取显示用的标题
     */
    val displayTitle: String
        get() = if (title.isBlank()) "未知标题" else title
    
    /**
     * 获取格式化的时长
     */
    val formattedDuration: String
        get() {
            val seconds = (duration / 1000).toInt()
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return String.format("%d:%02d", minutes, remainingSeconds)
        }
    
    /**
     * 检查是否有有效的文件路径
     */
    val isValid: Boolean
        get() = filePath.isNotBlank() && duration > 0
}
