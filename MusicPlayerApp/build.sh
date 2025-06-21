#!/bin/bash

# Apple Music Replica - 构建脚本
# 用于自动化构建Android APK

set -e  # 遇到错误立即停止

echo "🎵 Apple Music Replica - 构建脚本"
echo "=================================="

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 检查必要工具
check_tools() {
    echo -e "${BLUE}📋 检查构建环境...${NC}"
    
    if ! command -v java &> /dev/null; then
        echo -e "${RED}❌ Java未安装，请先安装JDK 8或更高版本${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}✅ Java环境正常${NC}"
    java -version
    echo ""
}

# 清理构建缓存
clean_build() {
    echo -e "${BLUE}🧹 清理构建缓存...${NC}"
    
    if [ -f "./gradlew" ]; then
        chmod +x ./gradlew
        ./gradlew clean
    else
        echo -e "${RED}❌ gradlew文件不存在${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}✅ 清理完成${NC}"
    echo ""
}

# 构建Debug版本
build_debug() {
    echo -e "${BLUE}🔨 构建Debug版本...${NC}"
    
    ./gradlew assembleDebug
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Debug APK构建成功${NC}"
        echo -e "${GREEN}📱 文件位置: app/build/outputs/apk/debug/app-debug.apk${NC}"
    else
        echo -e "${RED}❌ Debug APK构建失败${NC}"
        exit 1
    fi
    echo ""
}

# 构建Release版本
build_release() {
    echo -e "${BLUE}🚀 构建Release版本...${NC}"
    
    ./gradlew assembleRelease
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Release APK构建成功${NC}"
        echo -e "${GREEN}📱 文件位置: app/build/outputs/apk/release/app-release-unsigned.apk${NC}"
    else
        echo -e "${RED}❌ Release APK构建失败${NC}"
        exit 1
    fi
    echo ""
}

# 显示APK信息
show_apk_info() {
    echo -e "${BLUE}📊 APK文件信息${NC}"
    echo "===================="
    
    DEBUG_APK="app/build/outputs/apk/debug/app-debug.apk"
    RELEASE_APK="app/build/outputs/apk/release/app-release-unsigned.apk"
    
    if [ -f "$DEBUG_APK" ]; then
        DEBUG_SIZE=$(du -h "$DEBUG_APK" | cut -f1)
        echo -e "${GREEN}Debug APK: $DEBUG_SIZE${NC}"
    fi
    
    if [ -f "$RELEASE_APK" ]; then
        RELEASE_SIZE=$(du -h "$RELEASE_APK" | cut -f1)
        echo -e "${GREEN}Release APK: $RELEASE_SIZE${NC}"
    fi
    echo ""
}

# 安装到设备
install_debug() {
    echo -e "${BLUE}📱 安装Debug版本到设备...${NC}"
    
    if command -v adb &> /dev/null; then
        # 检查设备连接
        DEVICES=$(adb devices | grep -v "List of devices" | grep "device$" | wc -l)
        
        if [ $DEVICES -eq 0 ]; then
            echo -e "${YELLOW}⚠️  未检测到连接的Android设备${NC}"
            echo -e "${YELLOW}请确保设备已连接并开启USB调试${NC}"
            return
        fi
        
        ./gradlew installDebug
        
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✅ 应用安装成功${NC}"
        else
            echo -e "${RED}❌ 应用安装失败${NC}"
        fi
    else
        echo -e "${YELLOW}⚠️  ADB未安装，无法自动安装到设备${NC}"
    fi
    echo ""
}

# 显示使用说明
show_usage() {
    echo "使用方法:"
    echo "  $0 [选项]"
    echo ""
    echo "选项:"
    echo "  debug      - 仅构建Debug版本"
    echo "  release    - 仅构建Release版本"
    echo "  install    - 构建并安装Debug版本到设备"
    echo "  clean      - 仅清理构建缓存"
    echo "  help       - 显示此帮助信息"
    echo ""
    echo "不带参数运行时，会构建Debug和Release两个版本"
}

# 主函数
main() {
    case "${1:-all}" in
        "debug")
            check_tools
            clean_build
            build_debug
            show_apk_info
            ;;
        "release")
            check_tools
            clean_build
            build_release
            show_apk_info
            ;;
        "install")
            check_tools
            clean_build
            build_debug
            install_debug
            show_apk_info
            ;;
        "clean")
            check_tools
            clean_build
            ;;
        "help"|"-h"|"--help")
            show_usage
            ;;
        "all")
            check_tools
            clean_build
            build_debug
            build_release
            show_apk_info
            ;;
        *)
            echo -e "${RED}❌ 未知选项: $1${NC}"
            echo ""
            show_usage
            exit 1
            ;;
    esac
}

# 运行主函数
main "$@"

echo -e "${GREEN}🎉 构建完成！${NC}"
echo ""
echo -e "${BLUE}📖 使用说明:${NC}"
echo "1. 将APK文件传输到Android平板设备"
echo "2. 在设备上启用'未知来源'安装"
echo "3. 点击APK文件进行安装"
echo "4. 授予必要的存储权限"
echo "5. 享受Apple Music风格的音乐体验！"
echo ""
echo -e "${YELLOW}注意: 确保设备运行Android 9.0或更高版本${NC}"
