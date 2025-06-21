package com.applemusicreplica.core.common

/**
 * 应用全局常量
 */
object Constants {
    
    /**
     * 数据库相关
     */
    object Database {
        const val NAME = "apple_music_replica.db"
        const val VERSION = 1
    }
    
    /**
     * 通知相关
     */
    object Notifications {
        const val CHANNEL_ID = "music_playback"
        const val NOTIFICATION_ID = 1001
    }
    
    /**
     * 播放器相关
     */
    object Player {
        const val SEEK_FORWARD_INCREMENT = 15000L  // 15秒
        const val SEEK_BACKWARD_INCREMENT = 15000L // 15秒
        const val EXTENDED_WORD_THRESHOLD = 750L   // 延长音阈值：0.75秒
        const val LYRICS_UPDATE_INTERVAL = 100L    // 歌词更新间隔：100毫秒
    }
    
    /**
     * 文件相关
     */
    object Files {
        const val CACHE_DIR = "music_cache"
        const val LYRICS_DIR = "lyrics"
        const val ALBUM_ART_DIR = "album_art"
        
        val SUPPORTED_AUDIO_FORMATS = setOf(
            "mp3", "flac", "aac", "ogg", "wav", "m4a", "wma", "ape"
        )
        
        val SUPPORTED_LYRICS_FORMATS = setOf(
            "ttml", "lrc", "srt", "vtt"
        )
    }
    
    /**
     * UI相关
     */
    object UI {
        const val ANIMATION_DURATION_SHORT = 150L
        const val ANIMATION_DURATION_MEDIUM = 300L
        const val ANIMATION_DURATION_LONG = 500L
        
        const val BLUR_RADIUS = 20f
        const val BACKGROUND_ALPHA = 0.8f
        
        const val GRID_SPAN_COUNT_PHONE = 2
        const val GRID_SPAN_COUNT_TABLET = 4
    }
    
    /**
     * SharedPreferences键名
     */
    object Preferences {
        const val NAME = "apple_music_prefs"
        
        const val LAST_PLAYED_SONG_ID = "last_played_song_id"
        const val LAST_PLAYED_POSITION = "last_played_position"
        const val SHUFFLE_ENABLED = "shuffle_enabled"
        const val REPEAT_MODE = "repeat_mode"
        const val PLAYBACK_SPEED = "playback_speed"
        
        const val THEME_MODE = "theme_mode"
        const val DYNAMIC_COLORS = "dynamic_colors"
        const val SHOW_LYRICS = "show_lyrics"
        const val AUTO_SCROLL_LYRICS = "auto_scroll_lyrics"
        
        const val LIBRARY_SORT_ORDER = "library_sort_order"
        const val LIBRARY_VIEW_TYPE = "library_view_type"
    }
    
    /**
     * Intent Actions
     */
    object Actions {
        const val PLAY_PAUSE = "com.applemusicreplica.PLAY_PAUSE"
        const val NEXT = "com.applemusicreplica.NEXT"
        const val PREVIOUS = "com.applemusicreplica.PREVIOUS"
        const val STOP = "com.applemusicreplica.STOP"
        
        const val OPEN_PLAYER = "com.applemusicreplica.OPEN_PLAYER"
        const val OPEN_LIBRARY = "com.applemusicreplica.OPEN_LIBRARY"
    }
    
    /**
     * 权限相关
     */
    object Permissions {
        const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
        const val READ_MEDIA_AUDIO = android.Manifest.permission.READ_MEDIA_AUDIO
        const val WAKE_LOCK = android.Manifest.permission.WAKE_LOCK
        const val FOREGROUND_SERVICE = android.Manifest.permission.FOREGROUND_SERVICE
        const val FOREGROUND_SERVICE_MEDIA_PLAYBACK = android.Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK
    }
    
    /**
     * 网络相关
     */
    object Network {
        const val CONNECT_TIMEOUT = 10000L
        const val READ_TIMEOUT = 30000L
        const val WRITE_TIMEOUT = 30000L
    }
    
    /**
     * 错误代码
     */
    object ErrorCodes {
        const val PERMISSION_DENIED = 1001
        const val FILE_NOT_FOUND = 1002
        const val PLAYBACK_ERROR = 1003
        const val NETWORK_ERROR = 1004
        const val UNKNOWN_ERROR = 9999
    }
    
    /**
     * 排序类型
     */
    enum class SortOrder {
        TITLE_ASC,
        TITLE_DESC,
        ARTIST_ASC,
        ARTIST_DESC,
        ALBUM_ASC,
        ALBUM_DESC,
        DATE_ADDED_ASC,
        DATE_ADDED_DESC,
        DURATION_ASC,
        DURATION_DESC
    }
    
    /**
     * 视图类型
     */
    enum class ViewType {
        LIST,
        GRID,
        CARD
    }
    
    /**
     * 主题模式
     */
    enum class ThemeMode {
        LIGHT,
        DARK,
        SYSTEM
    }
}
