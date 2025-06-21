package com.applemusicreplica.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.applemusicreplica.core.model.Song

/**
 * 歌曲数据库实体
 */
@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0,
    
    @ColumnInfo(name = "title")
    val title: String = "",
    
    @ColumnInfo(name = "artist")
    val artist: String = "",
    
    @ColumnInfo(name = "album")
    val album: String = "",
    
    @ColumnInfo(name = "album_id")
    val albumId: Long = 0,
    
    @ColumnInfo(name = "duration")
    val duration: Long = 0,
    
    @ColumnInfo(name = "file_path")
    val filePath: String = "",
    
    @ColumnInfo(name = "album_art_path")
    val albumArtPath: String? = null,
    
    @ColumnInfo(name = "track_number")
    val trackNumber: Int = 0,
    
    @ColumnInfo(name = "year")
    val year: Int = 0,
    
    @ColumnInfo(name = "genre")
    val genre: String = "",
    
    @ColumnInfo(name = "size")
    val size: Long = 0,
    
    @ColumnInfo(name = "mime_type")
    val mimeType: String = "",
    
    @ColumnInfo(name = "date_added")
    val dateAdded: Long = 0,
    
    @ColumnInfo(name = "date_modified")
    val dateModified: Long = 0,
    
    @ColumnInfo(name = "is_loved")
    val isLoved: Boolean = false,
    
    @ColumnInfo(name = "play_count")
    val playCount: Int = 0,
    
    @ColumnInfo(name = "last_play_time")
    val lastPlayTime: Long = 0
) {
    /**
     * 转换为领域模型
     */
    fun toDomainModel(): Song {
        return Song(
            id = id,
            title = title,
            artist = artist,
            album = album,
            albumId = albumId,
            duration = duration,
            filePath = filePath,
            albumArtPath = albumArtPath,
            trackNumber = trackNumber,
            year = year,
            genre = genre,
            size = size,
            mimeType = mimeType,
            dateAdded = dateAdded,
            dateModified = dateModified,
            isLoved = isLoved,
            playCount = playCount,
            lastPlayTime = lastPlayTime
        )
    }
}

/**
 * 从领域模型转换为数据库实体
 */
fun Song.toEntity(): SongEntity {
    return SongEntity(
        id = id,
        title = title,
        artist = artist,
        album = album,
        albumId = albumId,
        duration = duration,
        filePath = filePath,
        albumArtPath = albumArtPath,
        trackNumber = trackNumber,
        year = year,
        genre = genre,
        size = size,
        mimeType = mimeType,
        dateAdded = dateAdded,
        dateModified = dateModified,
        isLoved = isLoved,
        playCount = playCount,
        lastPlayTime = lastPlayTime
    )
}
