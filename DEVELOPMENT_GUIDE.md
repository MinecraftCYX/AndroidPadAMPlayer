# Apple Music Replica - 开发指南

## 🚀 项目概述

本项目是一个完全复刻Apple Music iPad版布局的Android音乐播放器，专为Android平板设备优化设计。

### 核心特性亮点

1. **🎨 1:1复刻Apple Music iPad版布局**
   - 侧边栏导航设计
   - 响应式网格布局
   - Apple Music风格的视觉设计

2. **🌟 高级视觉效果**
   - Android 9+原生高斯模糊效果
   - 专辑封面颜色提取的动态背景
   - 流畅的界面过渡动画

3. **🎵 专业音乐功能**
   - 完整的音频元数据读取
   - TTML格式歌词逐字同步
   - 延长音（>0.75秒）高亮效果
   - 自动媒体库扫描

## 📋 开发环境设置

### 必需工具
- **Android Studio**: Hedgehog (2023.1.1) 或更新版本
- **JDK**: 8 或更高版本
- **Android SDK**: API 28-34
- **Gradle**: 8.2+

### 环境配置
1. 确保Android SDK已安装API 28-34
2. 启用Kotlin支持
3. 安装Android平板模拟器（推荐API 31+）

## 🏗️ 项目架构详解

### 架构模式
采用**MVVM + Clean Architecture**设计：

```
Presentation Layer (UI)
    ↓
Domain Layer (Business Logic)
    ↓
Data Layer (Repository + DataSource)
```

### 模块结构
```
app/                    # 应用主模块
├── ui/                # Jetpack Compose UI
├── di/                # Hilt依赖注入
└── service/           # 后台播放服务

core/
├── model/             # 数据模型定义
├── common/            # 通用工具类
├── data/              # 数据层实现
├── domain/            # 业务逻辑层
└── ui/                # UI组件库

feature/
├── library/           # 音乐库功能
├── player/            # 播放器功能
└── lyrics/            # 歌词功能
```

### 技术栈详情

#### UI层
- **Jetpack Compose**: 现代声明式UI框架
- **Material Design 3**: Google最新设计语言
- **Navigation Compose**: 页面导航管理
- **Hilt Navigation Compose**: 依赖注入集成

#### 数据层
- **Room**: 本地数据库存储
- **DataStore**: 用户偏好设置
- **MediaStore**: 系统媒体库访问
- **Kotlin Coroutines + Flow**: 异步数据流

#### 媒体处理
- **Media3/ExoPlayer**: 音频播放引擎
- **MediaMetadataRetriever**: 音频元数据提取
- **Palette API**: 专辑封面颜色提取
- **Glide**: 图片加载和缓存

## 🔧 构建与部署

### 快速构建
```bash
# 克隆项目后
cd MusicPlayerApp

# 构建Debug版本
./build.sh debug

# 构建Release版本
./build.sh release

# 构建并安装到设备
./build.sh install
```

### 手动构建
```bash
# 清理构建
./gradlew clean

# 构建Debug APK
./gradlew assembleDebug

# 构建Release APK
./gradlew assembleRelease

# 安装到连接的设备
./gradlew installDebug
```

### APK输出位置
- **Debug**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release**: `app/build/outputs/apk/release/app-release-unsigned.apk`

## 🎯 核心功能实现

### 1. Apple Music布局复刻

#### 侧边栏导航
- 使用`NavigationDrawer`组件
- 固定280dp宽度，匹配iPad版本
- 自定义选中状态和悬停效果

#### 主内容区域
- 使用`Row`布局分割侧边栏和内容区
- 响应式设计，适配不同屏幕尺寸
- 底部迷你播放器叠加显示

### 2. 高斯模糊效果实现

```kotlin
// Android 12+ 原生模糊
window.setAttributes {
    setBlurBehindRadius(20)
    setBlurBehindDimAmount(0.1f)
}

// 兼容性模糊（Android 9-11）
RenderScript.blur(context, bitmap, radius)
```

### 3. 动态背景颜色

```kotlin
// 颜色提取
val palette = Palette.from(albumBitmap).generate()
val dominantColor = palette.dominantSwatch?.rgb

// 背景渐变应用
Box(
    modifier = Modifier.background(
        Brush.verticalGradient(
            colors = listOf(
                Color(dominantColor).copy(alpha = 0.8f),
                Color.Transparent
            )
        )
    )
)
```

### 4. TTML歌词解析

```kotlin
data class LyricWord(
    val startTime: Long,
    val endTime: Long,
    val text: String
) {
    val isExtended: Boolean
        get() = (endTime - startTime) > 750L // 0.75秒
}
```

### 5. 媒体库扫描

```kotlin
// 使用MediaStore API扫描音频文件
val projection = arrayOf(
    MediaStore.Audio.Media.TITLE,
    MediaStore.Audio.Media.ARTIST,
    MediaStore.Audio.Media.ALBUM,
    MediaStore.Audio.Media.DURATION,
    MediaStore.Audio.Media.DATA
)

contentResolver.query(
    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    projection,
    null,
    null,
    MediaStore.Audio.Media.TITLE + " ASC"
)
```

## 🧪 测试指南

### 单元测试
```bash
./gradlew test
```

### UI测试
```bash
./gradlew connectedAndroidTest
```

### 测试覆盖率
```bash
./gradlew jacocoTestReport
```

## 📱 设备适配

### 支持的设备
- **主要目标**: Android平板设备 (7英寸+)
- **最低要求**: Android 9.0 (API 28)
- **推荐配置**: Android 12+ (API 31+)

### 屏幕适配
- 使用`dp`单位确保设备独立性
- 响应式布局设计
- 横屏模式优化

### 性能优化
- 大图片自动压缩
- 音频文件分页加载
- 内存缓存管理

## 🔍 调试技巧

### 启用调试模式
在`build.gradle`中添加：
```kotlin
buildConfigField "boolean", "DEBUG_MODE", "true"
```

### 日志输出
使用结构化日志：
```kotlin
Log.d("MusicPlayer", "Playing: ${song.title}")
```

### 性能监控
使用Android Studio Profiler监控：
- CPU使用率
- 内存占用
- 网络请求

## 🚧 常见问题解决

### 权限问题
确保在AndroidManifest.xml中声明：
```xml
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```

### 音频播放问题
检查MediaPlayer状态：
```kotlin
if (mediaPlayer.isPlaying) {
    // 处理播放逻辑
}
```

### 内存泄漏
使用LeakCanary检测：
```kotlin
if (BuildConfig.DEBUG) {
    LeakCanary.install(this)
}
```

## 📈 性能基准

### 启动时间
- 冷启动: < 2秒
- 热启动: < 0.5秒

### 内存使用
- 空闲状态: < 100MB
- 播放状态: < 150MB

### 电池消耗
- 后台播放: < 5% per hour

## 🔮 未来规划

### 短期目标
- [ ] 支持在线歌词下载
- [ ] 添加均衡器功能
- [ ] 支持播放列表管理

### 长期目标
- [ ] 云端同步功能
- [ ] AI推荐系统
- [ ] 社交分享功能

## 📚 学习资源

### 官方文档
- [Android Developer Guide](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design Guidelines](https://material.io/design)

### 推荐阅读
- Clean Architecture by Robert Martin
- Android Development Best Practices
- Kotlin Coroutines Guide

---

**注意**: 本项目仅供学习和技术交流使用，请尊重相关版权。
