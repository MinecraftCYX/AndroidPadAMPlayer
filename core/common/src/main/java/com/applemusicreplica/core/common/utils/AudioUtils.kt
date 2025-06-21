package com.applemusicreplica.core.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * 音频工具类
 */
object AudioUtils {
    
    /**
     * 音频元数据信息
     */
    data class AudioMetadata(
        val title: String?,
        val artist: String?,
        val album: String?,
        val albumArtist: String?,
        val duration: Long,
        val year: String?,
        val genre: String?,
        val trackNumber: String?,
        val mimeType: String?,
        val bitrate: String?,
        val sampleRate: String?,
        val albumArt: Bitmap?
    )
    
    /**
     * 从文件路径提取音频元数据
     */
    suspend fun extractMetadata(filePath: String): AudioMetadata? = withContext(Dispatchers.IO) {
        var retriever: MediaMetadataRetriever? = null
        try {
            retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)
            
            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            val albumArtist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val year = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR)
            val genre = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)
            val trackNumber = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER)
            val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
            val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)
            val sampleRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)
            
            val duration = durationStr?.toLongOrNull() ?: 0L
            
            // 提取专辑封面
            val albumArt = try {
                val artBytes = retriever.embeddedPicture
                artBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            } catch (e: Exception) {
                null
            }
            
            AudioMetadata(
                title = title?.takeIf { it.isNotBlank() },
                artist = artist?.takeIf { it.isNotBlank() },
                album = album?.takeIf { it.isNotBlank() },
                albumArtist = albumArtist?.takeIf { it.isNotBlank() },
                duration = duration,
                year = year?.takeIf { it.isNotBlank() },
                genre = genre?.takeIf { it.isNotBlank() },
                trackNumber = trackNumber?.takeIf { it.isNotBlank() },
                mimeType = mimeType?.takeIf { it.isNotBlank() },
                bitrate = bitrate?.takeIf { it.isNotBlank() },
                sampleRate = sampleRate?.takeIf { it.isNotBlank() },
                albumArt = albumArt
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                retriever?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 从Uri提取音频元数据
     */
    suspend fun extractMetadata(context: Context, uri: Uri): AudioMetadata? = withContext(Dispatchers.IO) {
        var retriever: MediaMetadataRetriever? = null
        try {
            retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)
            
            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            val albumArtist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val year = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR)
            val genre = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)
            val trackNumber = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER)
            val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
            val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)
            val sampleRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)
            
            val duration = durationStr?.toLongOrNull() ?: 0L
            
            // 提取专辑封面
            val albumArt = try {
                val artBytes = retriever.embeddedPicture
                artBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            } catch (e: Exception) {
                null
            }
            
            AudioMetadata(
                title = title?.takeIf { it.isNotBlank() },
                artist = artist?.takeIf { it.isNotBlank() },
                album = album?.takeIf { it.isNotBlank() },
                albumArtist = albumArtist?.takeIf { it.isNotBlank() },
                duration = duration,
                year = year?.takeIf { it.isNotBlank() },
                genre = genre?.takeIf { it.isNotBlank() },
                trackNumber = trackNumber?.takeIf { it.isNotBlank() },
                mimeType = mimeType?.takeIf { it.isNotBlank() },
                bitrate = bitrate?.takeIf { it.isNotBlank() },
                sampleRate = sampleRate?.takeIf { it.isNotBlank() },
                albumArt = albumArt
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                retriever?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 检查文件是否为有效的音频文件
     */
    fun isValidAudioFile(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            if (!file.exists() || !file.canRead()) return false
            
            val extension = file.extension.lowercase()
            return extension in setOf("mp3", "flac", "aac", "ogg", "wav", "m4a", "wma", "ape")
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 保存专辑封面到缓存目录
     */
    suspend fun saveAlbumArt(
        context: Context,
        bitmap: Bitmap,
        albumId: Long
    ): String? = withContext(Dispatchers.IO) {
        try {
            val cacheDir = File(context.cacheDir, "album_art")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            
            val file = File(cacheDir, "album_$albumId.jpg")
            file.outputStream().use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }
            
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * 从缓存目录加载专辑封面
     */
    suspend fun loadAlbumArt(
        context: Context,
        albumId: Long
    ): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val file = File(context.cacheDir, "album_art/album_$albumId.jpg")
            if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * 格式化比特率
     */
    fun formatBitrate(bitrate: String?): String {
        return try {
            val bitrateInt = bitrate?.toIntOrNull() ?: return "未知"
            val kbps = bitrateInt / 1000
            "${kbps} kbps"
        } catch (e: Exception) {
            "未知"
        }
    }
    
    /**
     * 格式化采样率
     */
    fun formatSampleRate(sampleRate: String?): String {
        return try {
            val sampleRateInt = sampleRate?.toIntOrNull() ?: return "未知"
            val khz = sampleRateInt / 1000.0
            String.format("%.1f kHz", khz)
        } catch (e: Exception) {
            "未知"
        }
    }
}
