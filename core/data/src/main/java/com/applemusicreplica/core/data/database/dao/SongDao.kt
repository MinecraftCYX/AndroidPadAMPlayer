package com.applemusicreplica.core.data.database.dao

import androidx.room.*
import com.applemusicreplica.core.data.database.entities.SongEntity
import kotlinx.coroutines.flow.Flow

/**
 * 歌曲数据访问对象
 */
@Dao
interface SongDao {
    
    /**
     * 获取所有歌曲
     */
    @Query("SELECT * FROM songs ORDER BY title ASC")
    fun getAllSongs(): Flow<List<SongEntity>>
    
    /**
     * 根据ID获取歌曲
     */
    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getSongById(id: Long): SongEntity?
    
    /**
     * 根据艺术家获取歌曲
     */
    @Query("SELECT * FROM songs WHERE artist = :artist ORDER BY album ASC, track_number ASC")
    fun getSongsByArtist(artist: String): Flow<List<SongEntity>>
    
    /**
     * 根据专辑获取歌曲
     */
    @Query("SELECT * FROM songs WHERE album_id = :albumId ORDER BY track_number ASC")
    fun getSongsByAlbum(albumId: Long): Flow<List<SongEntity>>
    
    /**
     * 根据专辑名称获取歌曲
     */
    @Query("SELECT * FROM songs WHERE album = :album ORDER BY track_number ASC")
    fun getSongsByAlbumName(album: String): Flow<List<SongEntity>>
    
    /**
     * 搜索歌曲
     */
    @Query("""
        SELECT * FROM songs 
        WHERE title LIKE '%' || :query || '%' 
        OR artist LIKE '%' || :query || '%' 
        OR album LIKE '%' || :query || '%'
        ORDER BY title ASC
    """)
    fun searchSongs(query: String): Flow<List<SongEntity>>
    
    /**
     * 获取最近添加的歌曲
     */
    @Query("SELECT * FROM songs ORDER BY date_added DESC LIMIT :limit")
    fun getRecentlyAddedSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    /**
     * 获取最近播放的歌曲
     */
    @Query("SELECT * FROM songs WHERE last_play_time > 0 ORDER BY last_play_time DESC LIMIT :limit")
    fun getRecentlyPlayedSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    /**
     * 获取最爱的歌曲
     */
    @Query("SELECT * FROM songs WHERE is_loved = 1 ORDER BY last_play_time DESC")
    fun getLovedSongs(): Flow<List<SongEntity>>
    
    /**
     * 获取播放次数最多的歌曲
     */
    @Query("SELECT * FROM songs WHERE play_count > 0 ORDER BY play_count DESC LIMIT :limit")
    fun getMostPlayedSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    /**
     * 根据时长范围获取歌曲
     */
    @Query("SELECT * FROM songs WHERE duration BETWEEN :minDuration AND :maxDuration ORDER BY title ASC")
    fun getSongsByDuration(minDuration: Long, maxDuration: Long): Flow<List<SongEntity>>
    
    /**
     * 根据年份获取歌曲
     */
    @Query("SELECT * FROM songs WHERE year = :year ORDER BY album ASC, track_number ASC")
    fun getSongsByYear(year: Int): Flow<List<SongEntity>>
    
    /**
     * 根据流派获取歌曲
     */
    @Query("SELECT * FROM songs WHERE genre = :genre ORDER BY artist ASC, album ASC, track_number ASC")
    fun getSongsByGenre(genre: String): Flow<List<SongEntity>>
    
    /**
     * 插入歌曲
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)
    
    /**
     * 批量插入歌曲
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<SongEntity>)
    
    /**
     * 更新歌曲
     */
    @Update
    suspend fun updateSong(song: SongEntity)
    
    /**
     * 批量更新歌曲
     */
    @Update
    suspend fun updateSongs(songs: List<SongEntity>)
    
    /**
     * 删除歌曲
     */
    @Delete
    suspend fun deleteSong(song: SongEntity)
    
    /**
     * 根据ID删除歌曲
     */
    @Query("DELETE FROM songs WHERE id = :id")
    suspend fun deleteSongById(id: Long)
    
    /**
     * 删除所有歌曲
     */
    @Query("DELETE FROM songs")
    suspend fun deleteAllSongs()
    
    /**
     * 更新歌曲的喜爱状态
     */
    @Query("UPDATE songs SET is_loved = :isLoved WHERE id = :id")
    suspend fun updateLoveStatus(id: Long, isLoved: Boolean)
    
    /**
     * 增加播放次数并更新最后播放时间
     */
    @Query("UPDATE songs SET play_count = play_count + 1, last_play_time = :playTime WHERE id = :id")
    suspend fun incrementPlayCount(id: Long, playTime: Long = System.currentTimeMillis())
    
    /**
     * 获取歌曲总数
     */
    @Query("SELECT COUNT(*) FROM songs")
    fun getSongCount(): Flow<Int>
    
    /**
     * 获取总时长
     */
    @Query("SELECT SUM(duration) FROM songs")
    fun getTotalDuration(): Flow<Long?>
    
    /**
     * 检查歌曲是否存在
     */
    @Query("SELECT EXISTS(SELECT 1 FROM songs WHERE file_path = :filePath)")
    suspend fun songExists(filePath: String): Boolean
    
    /**
     * 获取所有艺术家（去重）
     */
    @Query("SELECT DISTINCT artist FROM songs WHERE artist != '' ORDER BY artist ASC")
    fun getAllArtists(): Flow<List<String>>
    
    /**
     * 获取所有专辑（去重）
     */
    @Query("SELECT DISTINCT album FROM songs WHERE album != '' ORDER BY album ASC")
    fun getAllAlbums(): Flow<List<String>>
    
    /**
     * 获取所有流派（去重）
     */
    @Query("SELECT DISTINCT genre FROM songs WHERE genre != '' ORDER BY genre ASC")
    fun getAllGenres(): Flow<List<String>>
    
    /**
     * 获取所有年份（去重）
     */
    @Query("SELECT DISTINCT year FROM songs WHERE year > 0 ORDER BY year DESC")
    fun getAllYears(): Flow<List<Int>>
}
