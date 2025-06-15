# Android 10+ Storage

[![](https://jitpack.io/v/arnab-kundu/Storage.svg)](https://jitpack.io/#arnab-kundu/Storage)

## How to use

Step 1. Add it in your root build.gradle at the end of repositories(for Gradle 6.7 or lower):

```groovy
	allprojects {
		repositories {
			//...
			maven { url 'https://jitpack.io' }
		}
	}
```

Or For API 33(Gradle 6.8 or higher) in settings.gradle

```groovy
    dependencyResolutionManagement {
        //...
        repositories {
            //...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency

In `build.gradle` file add this line
```groovy
	dependencies {
            implementation 'com.github.arnab-kundu:storage:1.0.9'
	}
```
Or in `build.gradle.kts` file add this line
```kts
    dependencies {
        implementation("com.github.arnab-kundu:storage:1.0.9")
    }
```

Step 3. Use this below code blocks as per your requirement into any of Activity, Fragment, ViewModel, Repository, FileManager etc. 

1. Create text file
```kotlin
  val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)
  
  appFileManager.createFile(
        context = this,
        fileLocationCategory = FileLocationCategory.MEDIA_DIRECTORY,
        fileName = "TestFile",      
        fileExtension = "txt"
    )
```
2. Create folder
```kotlin
    val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

    appFileManager.createFolder(
        folderName = "SampleFolder", 
        path = filesDir.path
    )
```
3. Encrypt file
```kotlin
    val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)
    
    appFileManager.createFile(
        context = this,
        fileLocationCategory = FileLocationCategory.EXTERNAL_FILES_DIRECTORY,
        fileName = "SampleFile",
        fileExtension = "txt"
    )

    appFileManager.encryptFile(
        context = this,
        srcFilePath = filesDir.path+"/SampleFile.txt",
        encryptedFileName = "MyEncrypt"
    )
```
4. Decrypt file
```kotlin
    val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

    appFileManager.decryptFile(
        context = this,
        encryptedFilePath = "/storage/emulated/0/Android/media/${BuildConfig.APPLICATION_ID}/MyEncrypt.enc",
        outputFileName = "MyDecryptFile.txt"
    )
```
5. Zip list of file
```kotlin
    val inputPath = "/storage/emulated/0/Android/media/${BuildConfig.APPLICATION_ID}"

    val listOfFiles = arrayListOf<String>()
    listOfFiles.add("$inputPath/testFile.txt")
    listOfFiles.add("$inputPath/testImage.jpeg")
    listOfFiles.add("$inputPath/big_buck_bunny_240p_10mb.mp4")

    fileManager.zipListOfFiles(listOfFiles, "$inputPath${File.separator}zipFileName.zip")
```
6. Unzip file
```kotlin
    val inputPath = "/storage/emulated/0/Android/media/${BuildConfig.APPLICATION_ID}"
    
    fileManager.unZipFile(
        zipFilePath = "/storage/emulated/0/Android/media/${BuildConfig.APPLICATION_ID}/10mb.zip",
        extractLocationPath = "/storage/emulated/0/Android/media/${BuildConfig.APPLICATION_ID}/"
    )
```
## :hammer_and_wrench: Build Tool - Android Studio

- Android Studio Meerkat | 2024.3.1 Patch 1
- MINIMUM SDK version: 24
- TARGET SDK version: 35
- JDK version: 17

## :dart: Target API 

Compatible with Android

- Android 10 (API 29)
- Android 11 (API 30)
- Android 12 (API 31)
- Android 12L (API 32)
- Android 13 (API 33)
- Android 14 (API 34)
- Android 15 (API 35)