package com.applemusicreplica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 资料库屏幕 - 复刻Apple Music的资料库页面
 */
@Composable
fun LibraryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // 页面标题
        Text(
            text = "资料库",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // 快速访问区域
        QuickAccessSection()
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 最近添加区域
        RecentlyAddedSection()
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 专辑网格
        AlbumsGridSection()
    }
}

/**
 * 快速访问区域
 */
@Composable
private fun QuickAccessSection() {
    Text(
        text = "快速访问",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.height(200.dp)
    ) {
        item {
            QuickAccessCard(
                title = "最近播放",
                icon = Icons.Default.History,
                onClick = { /* 处理点击 */ }
            )
        }
        item {
            QuickAccessCard(
                title = "最爱",
                icon = Icons.Default.Favorite,
                onClick = { /* 处理点击 */ }
            )
        }
        item {
            QuickAccessCard(
                title = "艺术家",
                icon = Icons.Default.Person,
                onClick = { /* 处理点击 */ }
            )
        }
        item {
            QuickAccessCard(
                title = "专辑",
                icon = Icons.Default.Album,
                onClick = { /* 处理点击 */ }
            )
        }
    }
}

/**
 * 快速访问卡片
 */
@Composable
private fun QuickAccessCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * 最近添加区域
 */
@Composable
private fun RecentlyAddedSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "最近添加",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        
        TextButton(onClick = { /* 查看全部 */ }) {
            Text("查看全部")
        }
    }
    
    // 水平滚动的专辑列表
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        items(10) { index ->
            RecentAlbumCard(
                albumTitle = "专辑 ${index + 1}",
                artistName = "艺术家 ${index + 1}",
                onClick = { /* 处理点击 */ }
            )
        }
    }
}

/**
 * 最近专辑卡片
 */
@Composable
private fun RecentAlbumCard(
    albumTitle: String,
    artistName: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 专辑封面占位符
        Surface(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Icon(
                Icons.Default.Album,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = albumTitle,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = artistName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

/**
 * 专辑网格区域
 */
@Composable
private fun AlbumsGridSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "专辑",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        
        Row {
            IconButton(onClick = { /* 切换视图 */ }) {
                Icon(Icons.Default.GridView, contentDescription = "网格视图")
            }
            IconButton(onClick = { /* 排序 */ }) {
                Icon(Icons.Default.Sort, contentDescription = "排序")
            }
        }
    }
    
    // 专辑网格
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        items(20) { index ->
            AlbumGridCard(
                albumTitle = "专辑 ${index + 1}",
                artistName = "艺术家 ${index + 1}",
                onClick = { /* 处理点击 */ }
            )
        }
    }
}

/**
 * 专辑网格卡片
 */
@Composable
private fun AlbumGridCard(
    albumTitle: String,
    artistName: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        // 专辑封面
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Icon(
                Icons.Default.Album,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = albumTitle,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = artistName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

// 添加缺失的import
import androidx.compose.foundation.clickable
