package com.arnab.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arnab.storage.AppFileManager
import com.arnab.storage.FileLocationCategory
import com.arnab.storage.FileType
import java.io.File

class MainActivity : AppCompatActivity() {
    @Suppress("UNUSED_VARIABLE")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

        // region Visible files
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.MEDIA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.OBB_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.EXTERNAL_CACHE_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.EXTERNAL_FILES_DIRECTORY,
            fileName = "SampleFile",
            fileExtension = FileType.TXT
        )

        appFileManager.encryptFile(
            context = this,
            srcFilePath = filesDir.path + "/TestFile.txt",
            encryptedFileName = "MyEncrypt"
        )

        /*appFileManager.decryptFile(
            context = this,
            encryptedFilePath = "/storage/emulated/0/Android/media/com.arnab.demo/MyEncrypt.enc",
            outputFileName = "MyDecryptFile.txt"
        )*/
        // endregion

        // region Invisible files
        appFileManager.createFolder(folderName = "SampleFolder", path = filesDir.path)

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.FILES_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )

        val file = appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.DATA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )

        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.CACHE_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )
        appFileManager.deleteFolder(directory = File(filesDir.path, "SampleFolder"))
        // endregion

        // region External files directory
        /**
         *  Use this permissions in Manifest to save file in External directory
         *
         *  <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
         *  <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
         *  <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
         *  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
         *
         */
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.DOCUMENT_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.TXT
        )
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.PICTURES_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.JPG
        )
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.PICTURES_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.PNG
        )
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.MUSIC_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.MP3
        )
        appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.VIDEOS_DIRECTORY,
            fileName = "TestFile",
            fileExtension = FileType.MP4
        )
        // endregion
    }
}