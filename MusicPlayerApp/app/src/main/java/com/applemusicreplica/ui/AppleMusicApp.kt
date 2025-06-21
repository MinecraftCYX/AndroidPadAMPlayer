package com.applemusicreplica.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.applemusicreplica.ui.screens.*

/**
 * 主应用组件 - 复刻Apple Music iPad版布局
 */
@Composable
fun AppleMusicApp() {
    val navController = rememberNavController()
    
    // 使用Row布局来模拟iPad的侧边栏+主内容区域布局
    Row(modifier = Modifier.fillMaxSize()) {
        // 左侧导航栏（侧边栏）
        NavigationSidebar(
            modifier = Modifier.width(280.dp),
            navController = navController
        )
        
        // 主内容区域
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            NavHost(
                navController = navController,
                startDestination = "library"
            ) {
                composable("library") {
                    LibraryScreen()
                }
                composable("radio") {
                    RadioScreen()
                }
                composable("search") {
                    SearchScreen()
                }
                composable("browse") {
                    BrowseScreen()
                }
                composable("now_playing") {
                    NowPlayingScreen()
                }
            }
            
            // 底部迷你播放器（始终显示）
            MiniPlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
            )
        }
    }
}

/**
 * 左侧导航栏组件
 */
@Composable
private fun NavigationSidebar(
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    Surface(
        modifier = modifier.fillMaxHeight(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Apple Music 标题
            Text(
                text = "Apple Music",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            // 导航项目
            val items = listOf(
                NavigationItem("library", "资料库", Icons.Default.LibraryMusic),
                NavigationItem("radio", "广播", Icons.Default.Radio),
                NavigationItem("search", "搜索", Icons.Default.Search),
                NavigationItem("browse", "浏览", Icons.Default.Explore)
            )
            
            items.forEach { item ->
                NavigationSidebarItem(
                    icon = item.icon,
                    label = item.label,
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 正在播放按钮
            NavigationSidebarItem(
                icon = Icons.Default.PlayCircle,
                label = "正在播放",
                selected = currentDestination?.hierarchy?.any { it.route == "now_playing" } == true,
                onClick = {
                    navController.navigate("now_playing")
                }
            )
        }
    }
}

/**
 * 侧边栏导航项组件
 */
@Composable
private fun NavigationSidebarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = null) },
        label = { Text(label) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

/**
 * 迷你播放器组件
 */
@Composable
private fun MiniPlayer(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            // 歌曲信息
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                // 专辑封面占位符
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "歌曲标题",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "艺术家",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            
            // 播放控制按钮
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* 上一首 */ }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "上一首")
                }
                
                IconButton(onClick = { /* 播放/暂停 */ }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "播放")
                }
                
                IconButton(onClick = { /* 下一首 */ }) {
                    Icon(Icons.Default.SkipNext, contentDescription = "下一首")
                }
            }
        }
    }
}

/**
 * 导航项数据类
 */
private data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
