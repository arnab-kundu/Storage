package com.arnab.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arnab.storage.AppFileManager
import com.arnab.storage.FileLocationCategory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.MEDIA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )
    }
}