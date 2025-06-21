package com.applemusicreplica

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用主类
 */
@HiltAndroidApp
class MusicApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // 初始化应用配置
        initializeApp()
    }
    
    private fun initializeApp() {
        // 这里可以添加应用初始化逻辑
        // 例如：崩溃报告、分析工具、主题初始化等
    }
}
