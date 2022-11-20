# Android 10 Storage

[![](https://jitpack.io/v/arnab-kundu/Storage.svg)](https://jitpack.io/#arnab-kundu/Storage)

## How to use

Step 1. Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Or For API 33 in settings.gradle

```
    dependencyResolutionManagement {
        ...
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency

```
	dependencies {
		implementation 'com.github.arnab-kundu:Storage:1.0.2'
	}
```

Step 3. Add this in your MainActivity

```
  val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)
  
  appFileManager.createFile(
            context = this,
            fileLocationCategory = FileLocationCategory.MEDIA_DIRECTORY,
            fileName = "TestFile",
            fileExtension = "txt"
        )
```

## Build Tool

- Android Studio Dolphin | 2021.3.1 Patch 1
- MINIMUM SDK version: 24
- TARGET SDK version: 33

## Support

- Android 10
- Android 11
- Android 12
- android 13
