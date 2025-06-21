package com.applemusicreplica.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 歌词行数据模型
 */
@Parcelize
data class LyricLine(
    val startTime: Long = 0, // 开始时间，毫秒
    val endTime: Long = 0,   // 结束时间，毫秒
    val text: String = "",   // 歌词文本
    val words: List<LyricWord> = emptyList() // 逐字时间信息
) : Parcelable {
    
    /**
     * 检查指定时间是否在这一行的时间范围内
     */
    fun isActiveAt(timeMs: Long): Boolean {
        return timeMs in startTime..endTime
    }
    
    /**
     * 获取当前时间下的活跃单词索引
     */
    fun getActiveWordIndex(timeMs: Long): Int {
        if (!isActiveAt(timeMs)) return -1
        
        for (i in words.indices) {
            val word = words[i]
            if (timeMs in word.startTime..word.endTime) {
                return i
            }
        }
        return -1
    }
    
    /**
     * 检查是否是延长音（单个字词超过0.75秒）
     */
    val hasExtendedWords: Boolean
        get() = words.any { it.isExtended }
}

/**
 * 歌词单词数据模型（用于逐字高亮）
 */
@Parcelize
data class LyricWord(
    val startTime: Long = 0, // 开始时间，毫秒
    val endTime: Long = 0,   // 结束时间，毫秒
    val text: String = ""    // 单词文本
) : Parcelable {
    
    /**
     * 单词持续时间
     */
    val duration: Long
        get() = endTime - startTime
    
    /**
     * 检查是否是延长音（超过0.75秒）
     */
    val isExtended: Boolean
        get() = duration > 750 // 0.75秒 = 750毫秒
    
    /**
     * 检查指定时间是否在这个单词的时间范围内
     */
    fun isActiveAt(timeMs: Long): Boolean {
        return timeMs in startTime..endTime
    }
}

/**
 * 完整歌词数据模型
 */
@Parcelize
data class Lyrics(
    val songId: Long = 0,
    val title: String = "",
    val artist: String = "",
    val lines: List<LyricLine> = emptyList(),
    val language: String = "zh", // 默认中文
    val source: String = "", // 歌词来源
    val isTranslated: Boolean = false // 是否为翻译版本
) : Parcelable {
    
    /**
     * 获取当前时间下的活跃歌词行索引
     */
    fun getActiveLineIndex(timeMs: Long): Int {
        for (i in lines.indices) {
            if (lines[i].isActiveAt(timeMs)) {
                return i
            }
        }
        return -1
    }
    
    /**
     * 获取当前时间下的活跃歌词行
     */
    fun getActiveLine(timeMs: Long): LyricLine? {
        val index = getActiveLineIndex(timeMs)
        return if (index >= 0) lines[index] else null
    }
    
    /**
     * 检查是否为空歌词
     */
    val isEmpty: Boolean
        get() = lines.isEmpty() || lines.all { it.text.isBlank() }
    
    /**
     * 检查是否支持逐字显示
     */
    val supportsWordByWord: Boolean
        get() = lines.any { it.words.isNotEmpty() }
    
    /**
     * 获取歌词总时长
     */
    val totalDuration: Long
        get() = lines.maxOfOrNull { it.endTime } ?: 0
}

/**
 * 歌词加载状态
 */
sealed class LyricsState {
    object Loading : LyricsState()
    object NotFound : LyricsState()
    data class Success(val lyrics: Lyrics) : LyricsState()
    data class Error(val message: String) : LyricsState()
}
