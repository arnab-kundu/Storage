package com.arnab.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arnab.storage.AppFileManager
import com.arnab.storage.FileLocationCategory
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

        // region Visible files
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.MEDIA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.OBB_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.EXTERNAL_CACHE_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.EXTERNAL_FILES_DIRECTORY,
            fileName = "SampleFile",
            fileExtension = "txt"
        )

        appFileManager.encryptFile(
            context = this,
            srcFilePath = filesDir.path + "/TestFile.txt",
            encryptedFileName = "MyEncrypt"
        )

        appFileManager.decryptFile(
            context = this,
            encryptedFilePath = "/storage/emulated/0/Android/media/com.arnab.demo/MyEncrypt.enc",
            outputFileName = "MyDecryptFile.txt"
        )
        // endregion

        // region Invisible files
        appFileManager.createFolder(folderName = "SampleFolder", path = filesDir.path)

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.FILES_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )

        val file = appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.DATA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.CACHE_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )
        appFileManager.deleteFolder(directory = File(filesDir.path, "SampleFolder"))
        // endregion
    }
}