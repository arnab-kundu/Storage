# Android 10 Storage

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
Step 2. Add the dependency
```
	dependencies {
		implementation 'com.github.arnab-kundu:Storage:1.0.1'
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
## Support
- Android 10
- Android 11
- Android 12
- android 13
