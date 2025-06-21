# Apple Music Replica - Android音乐播放器

一个完全复刻Apple Music iPad版布局的Android平板音乐播放器应用。

## 🎵 功能特性

### 核心功能
- ✅ **1:1复刻Apple Music iPad版布局**
- ✅ **侧边栏导航** - 完美复刻iPad版的侧边栏设计
- ✅ **资料库管理** - 自动扫描和展示音乐库
- ✅ **高斯模糊效果** - 兼容Android 9+的原生高斯模糊
- ✅ **动态背景** - 根据专辑封面自动调整背景色彩
- ✅ **歌词同步显示** - 支持TTML格式逐字高亮
- ✅ **延长音高亮** - 单个字词超过0.75秒的特殊高亮效果

### 高级特性
- 🎨 **流动背景效果** - 封面颜色提取和背景动态变化
- 📱 **平板优化** - 专为Android平板设备优化的响应式布局
- 🎵 **音频元数据读取** - 自动读取歌曲标签内的封面、标题、艺术家信息
- 📝 **TTML歌词支持** - 支持时间同步的逐字歌词显示
- 🎯 **精准播放控制** - 支持播放、暂停、上下曲、进度控制

## 🛠️ 技术架构

### 核心技术栈
- **UI框架**: Jetpack Compose + Material Design 3
- **架构模式**: MVVM + Clean Architecture
- **依赖注入**: Hilt
- **数据库**: Room
- **音频播放**: Media3/ExoPlayer
- **图像处理**: Glide + Palette API
- **异步处理**: Kotlin Coroutines + Flow

### 项目结构
```
app/                    # 主应用模块
├── ui/                # UI界面
│   ├── theme/         # 主题配置
│   ├── screens/       # 各页面实现
│   └── components/    # 可复用组件
└── service/           # 播放服务

core/                  # 核心模块
├── model/             # 数据模型
├── common/            # 通用工具
├── data/              # 数据层
├── domain/            # 业务逻辑层
└── ui/                # UI组件库

feature/               # 功能模块
├── library/           # 资料库功能
├── player/            # 播放器功能
└── lyrics/            # 歌词功能
```

## 📱 系统要求

- **最低系统版本**: Android 9.0 (API 28)
- **推荐系统版本**: Android 12+ (API 31+)
- **设备类型**: Android平板设备 (推荐屏幕尺寸 ≥ 7英寸)
- **存储空间**: 至少50MB可用空间
- **权限要求**: 读取外部存储/媒体音频权限

## 🚀 快速开始

### 环境准备
1. **Android Studio**: Hedgehog | 2023.1.1 或更高版本
2. **JDK**: JDK 8 或更高版本
3. **Android SDK**: API 28-34
4. **Gradle**: 8.2+

### 构建步骤

1. **克隆项目**
   ```bash
   git clone [项目地址]
   cd MusicPlayerApp
   ```

2. **导入项目**
   - 打开Android Studio
   - 选择 "Open an Existing Project"
   - 选择项目根目录

3. **同步依赖**
   ```bash
   ./gradlew build
   ```

4. **构建APK**
   ```bash
   # Debug版本
   ./gradlew assembleDebug
   
   # Release版本
   ./gradlew assembleRelease
   ```

5. **安装到设备**
   ```bash
   ./gradlew installDebug
   ```

### 快速构建脚本
```bash
#!/bin/bash
# 清理并构建项目
./gradlew clean
./gradlew assembleRelease

# APK文件位置
echo "APK已生成: app/build/outputs/apk/release/app-release.apk"
```

## 📁 文件支持

### 音频格式
- MP3, FLAC, AAC, OGG, WAV, M4A, WMA, APE

### 歌词格式
- **主要支持**: TTML (逐字同步)
- **兼容格式**: LRC, SRT, VTT

### 歌词文件命名规则
歌词文件需要与音频文件同名，放置在相同目录下：
```
音乐文件夹/
├── 歌曲.mp3
└── 歌曲.ttml     # 对应的歌词文件
```

## 🎨 界面预览

### 主界面布局
- **左侧侧边栏**: 导航菜单（资料库、广播、搜索、浏览）
- **主内容区**: 动态内容展示区域
- **底部播放栏**: 迷你播放器控制

### 资料库页面
- **快速访问**: 最近播放、最爱、艺术家、专辑
- **最近添加**: 水平滚动的专辑卡片
- **专辑网格**: 响应式专辑网格布局

## ⚙️ 配置说明

### 权限配置
应用需要以下权限：
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```

### 媒体库扫描
应用会自动扫描以下目录：
- `/storage/emulated/0/Music/`
- `/storage/emulated/0/Download/`
- 以及其他包含音乐文件的标准目录

## 🔧 开发指南

### 添加新功能
1. 在相应的feature模块中添加功能实现
2. 更新相关的数据模型（core/model）
3. 实现对应的UI组件
4. 添加必要的测试

### 自定义主题
修改 `ui/theme/Theme.kt` 中的颜色配置：
```kotlin
private val AppleMusicRed = Color(0xFFFA2D48)
// 可以修改为其他颜色
```

### 调试模式
在 `build.gradle` 中启用调试功能：
```kotlin
buildConfigField "boolean", "DEBUG_MODE", "true"
```

## 📋 已知问题与解决方案

### 常见问题
1. **权限被拒绝**: 确保在设置中授予存储权限
2. **音乐未显示**: 检查音乐文件格式是否支持
3. **歌词不显示**: 确保歌词文件与音乐文件同名并放在同一目录

### 性能优化
- 大量音乐文件时，考虑开启分页加载
- 在低配置设备上可以关闭高斯模糊效果
- 定期清理专辑封面缓存

## 🤝 贡献指南

欢迎提交Issue和Pull Request来改进这个项目！

### 开发规范
- 遵循Kotlin编码规范
- 使用Jetpack Compose最佳实践
- 确保新功能有对应的测试
- 保持代码注释的完整性

## 📄 许可证

本项目仅供学习和研究使用。请尊重Apple Music的版权和商标权。

## 🙏 致谢

- Apple Music for the original design inspiration
- Android Team for the excellent development tools
- Open source community for the amazing libraries

---

**注意**: 本应用是Apple Music的非官方复刻版本，仅用于学习和技术展示目的。
