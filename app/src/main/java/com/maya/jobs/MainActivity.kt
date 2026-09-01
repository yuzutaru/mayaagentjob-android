package com.maya.jobs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.maya.jobs.ui.navigation.MayaApp
import com.maya.jobs.ui.theme.MayaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as MayaApplication
        setContent {
            MayaTheme {
                MayaApp(jobRepository = app.jobRepository, portfolioRepository = app.portfolioRepository)
            }
        }
    }
}
