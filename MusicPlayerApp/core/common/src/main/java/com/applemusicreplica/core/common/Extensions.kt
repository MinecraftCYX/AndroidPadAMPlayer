package com.applemusicreplica.core.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

/**
 * Context扩展函数
 */

/**
 * 检查是否为平板设备
 */
fun Context.isTablet(): Boolean {
    return resources.configuration.smallestScreenWidthDp >= 600
}

/**
 * 将dp转换为px
 */
fun Context.dpToPx(dp: Float): Int {
    val density = resources.displayMetrics.density
    return (dp * density).toInt()
}

/**
 * 将px转换为dp
 */
fun Context.pxToDp(px: Float): Int {
    val density = resources.displayMetrics.density
    return (px / density).toInt()
}

/**
 * String扩展函数
 */

/**
 * 检查字符串是否为空或只包含空白字符
 */
fun String?.isBlankOrEmpty(): Boolean {
    return this.isNullOrBlank()
}

/**
 * 获取文件扩展名
 */
fun String.getFileExtension(): String {
    return substringAfterLast('.', "")
}

/**
 * 检查是否为音频文件
 */
fun String.isAudioFile(): Boolean {
    val audioExtensions = setOf("mp3", "flac", "aac", "ogg", "wav", "m4a", "wma", "ape")
    return getFileExtension().lowercase(Locale.getDefault()) in audioExtensions
}

/**
 * 检查是否为歌词文件
 */
fun String.isLyricsFile(): Boolean {
    val lyricsExtensions = setOf("lrc", "ttml", "srt", "vtt")
    return getFileExtension().lowercase(Locale.getDefault()) in lyricsExtensions
}

/**
 * Long扩展函数（用于时间格式化）
 */

/**
 * 将毫秒转换为可读的时间格式
 */
fun Long.formatDuration(): String {
    val seconds = (this / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    
    return when {
        hours > 0 -> {
            val remainingMinutes = minutes % 60
            val remainingSeconds = seconds % 60
            String.format("%d:%02d:%02d", hours, remainingMinutes, remainingSeconds)
        }
        else -> {
            val remainingSeconds = seconds % 60
            String.format("%d:%02d", minutes, remainingSeconds)
        }
    }
}

/**
 * 将毫秒转换为简短的时间格式（仅分钟和秒）
 */
fun Long.formatShortDuration(): String {
    val seconds = (this / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}

/**
 * File扩展函数
 */

/**
 * 检查文件是否存在且可读
 */
fun File.isReadable(): Boolean {
    return exists() && canRead() && isFile
}

/**
 * 获取同名但不同扩展名的文件
 */
fun File.getSibling(newExtension: String): File {
    val nameWithoutExtension = nameWithoutExtension
    return File(parent, "$nameWithoutExtension.$newExtension")
}

/**
 * 查找同名的歌词文件
 */
fun File.findLyricsFile(): File? {
    val lyricsExtensions = listOf("ttml", "lrc", "srt", "vtt")
    
    for (extension in lyricsExtensions) {
        val lyricsFile = getSibling(extension)
        if (lyricsFile.isReadable()) {
            return lyricsFile
        }
    }
    return null
}

/**
 * Uri扩展函数
 */

/**
 * 将Uri转换为File（如果可能）
 */
fun Uri.toFile(): File? {
    return if (scheme == "file") {
        path?.let { File(it) }
    } else {
        null
    }
}

/**
 * Bitmap扩展函数（用于封面颜色提取）
 */

/**
 * 从Bitmap提取主要颜色
 */
suspend fun Bitmap.extractColors(): ColorPalette = withContext(Dispatchers.Default) {
    val palette = Palette.from(this@extractColors).generate()
    
    ColorPalette(
        dominant = palette.dominantSwatch?.rgb ?: Color.BLACK,
        vibrant = palette.vibrantSwatch?.rgb ?: Color.BLACK,
        darkVibrant = palette.darkVibrantSwatch?.rgb ?: Color.BLACK,
        lightVibrant = palette.lightVibrantSwatch?.rgb ?: Color.BLACK,
        muted = palette.mutedSwatch?.rgb ?: Color.GRAY,
        darkMuted = palette.darkMutedSwatch?.rgb ?: Color.GRAY,
        lightMuted = palette.lightMutedSwatch?.rgb ?: Color.GRAY
    )
}

/**
 * Drawable扩展函数
 */

/**
 * 将Drawable转换为Bitmap
 */
fun Drawable.toBitmap(): Bitmap? {
    return if (this is BitmapDrawable) {
        bitmap
    } else {
        null
    }
}

/**
 * Collection扩展函数
 */

/**
 * 安全地获取列表中的元素
 */
fun <T> List<T>.safeGet(index: Int): T? {
    return if (index in 0 until size) get(index) else null
}

/**
 * 随机打乱列表但保持当前元素在第一位
 */
fun <T> List<T>.shuffleKeepingFirst(currentIndex: Int): List<T> {
    if (isEmpty() || currentIndex < 0 || currentIndex >= size) return this.shuffled()
    
    val mutableList = this.toMutableList()
    val currentItem = mutableList.removeAt(currentIndex)
    mutableList.shuffle()
    mutableList.add(0, currentItem)
    return mutableList
}

/**
 * 数据类：颜色调色板
 */
data class ColorPalette(
    val dominant: Int,
    val vibrant: Int,
    val darkVibrant: Int,
    val lightVibrant: Int,
    val muted: Int,
    val darkMuted: Int,
    val lightMuted: Int
) {
    /**
     * 获取最适合作为背景的颜色
     */
    val backgroundColor: Int
        get() = darkVibrant.takeIf { it != Color.BLACK } ?: darkMuted.takeIf { it != Color.GRAY } ?: dominant
    
    /**
     * 获取最适合作为文本的颜色
     */
    val textColor: Int
        get() = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
    
    /**
     * 检查颜色是否为深色
     */
    private fun isColorDark(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }
}
