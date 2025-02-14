# 组件化+MVVM框架
## 模块划分

主要分为基础库、中间层和应用层

应用层：就是一层壳

中间层（common）：是对一些基础库的封装和一些公用的类、方法等

## 统一依赖便于管理

新建一个assembly.gradle文件，这里统一管理所有组件的gradle

![image-20230331143514094](https://cdn.jsdelivr.net/gh/QminCode/image@main/image-20230331143514094.png)

```kotlin
project.ext {
    //是否允许module单独调试
    isModuleDebug = false
    moduleName = ""//单独调试module名
    //基础信息配置
    compileSdkVersion = 31
    buildToolsVersion = "30.0.2"
    minSdkVersion = 21
    targetSdkVersion = 31
    applicationId = "com.abner.assembly"
    versionCode = 1
    versionName = "1.0.0"

    //设置app配置
    setAppDefaultConfig = {
        extension ->
            //指定为application
            extension.apply plugin: 'com.android.application'
            extension.description "app"

            //公共的apply 主要是用于三方库
            extension.apply plugin: 'kotlin-android'
            extension.apply plugin: 'kotlin-parcelize'
            extension.apply plugin: 'kotlin-kapt'

            appImplementation = "app"
            //设置项目的android
            setAppAndroidConfig extension.android
            //设置项目的三方库依赖
            setDependencies extension.dependencies

    }

    //设置application 公共的android配置
    setAppAndroidConfig = {
        extension ->
            extension.compileSdkVersion project.ext.compileSdkVersion
            extension.buildToolsVersion project.ext.buildToolsVersion
            extension.defaultConfig {
                applicationId project.ext.applicationId
                minSdkVersion project.ext.minSdkVersion
                targetSdkVersion project.ext.targetSdkVersion
                versionCode project.ext.versionCode
                versionName project.ext.versionName
                extension.flavorDimensions "versionCode"
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments = [AROUTER_MODULE_NAME: project.getName()]
                    }
                }
            }

            extension.compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }
            extension.kotlinOptions {
                jvmTarget = '1.8'
            }

            extension.buildFeatures.dataBinding = true
    }

    //动态改变，用于单模块调试
    setAppOrLibDefaultConfig = {
        extension ->
            if (project.ext.isModuleDebug && project.ext.moduleName == project.name) {
                extension.apply plugin: 'com.android.application'
                extension.description "app"
            } else {
                extension.apply plugin: 'com.android.library'
                extension.description "lib"

            }
            extension.apply plugin: 'kotlin-android'
            extension.apply plugin: 'kotlin-parcelize'
            extension.apply plugin: 'kotlin-kapt'

            appImplementation = project.name

            //设置通用Android配置
            setAppOrLibAndroidConfig extension.android
            //设置通用依赖配置
            setDependencies extension.dependencies
    }

    //设置通用的 android配置（可作为project单独调试）
    setAppOrLibAndroidConfig = {
        extension ->
            extension.compileSdkVersion project.ext.compileSdkVersion
            extension.buildToolsVersion project.ext.buildToolsVersion
            extension.defaultConfig {
                minSdkVersion project.ext.minSdkVersion
                targetSdkVersion project.ext.targetSdkVersion
                versionCode project.ext.versionCode
                versionName project.ext.versionName
                extension.flavorDimensions "versionCode"
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                //ARouter 编译生成路由
                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments = [AROUTER_MODULE_NAME: project.getName()]
                    }
                }
            }

            //使用的jdk版本
            extension.compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }
            extension.kotlinOptions {
                jvmTarget = '1.8'
            }

            //动态改变清单文件资源指向
            extension.sourceSets {
                main {
                    if (project.ext.isModuleDebug && project.ext.moduleName == project.name) {
                        manifest.srcFile 'src/main/manifest/AndroidManifest.xml'
                    } else {
                        manifest.srcFile 'src/main/AndroidManifest.xml'
                    }
                }
            }

    }


    //公用的三方库依赖，慎重引入，主要引入基础库依赖
    setDependencies = {
        extension ->
            extension.implementation fileTree(dir: 'libs', include: ['*.jar'])
            extension.implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
            extension.implementation 'androidx.core:core-ktx:1.3.1'
            extension.implementation 'androidx.appcompat:appcompat:1.3.1'
            extension.implementation 'com.google.android.material:material:1.4.0'
            extension.implementation 'androidx.constraintlayout:constraintlayout:2.0.1'
            extension.testImplementation 'junit:junit:4.+'
            extension.androidTestImplementation 'androidx.test.ext:junit:1.1.2'
            extension.androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
            // ViewModel
            extension.implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
            // ViewModel utilities for Compose
            extension.implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
            // LiveData
            extension.implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
            // Lifecycles only (without ViewModel or LiveData)
            extension.implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
            // Lifecycle utilities for Compose
            extension.implementation "androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version"

            // Saved state module for ViewModel
            extension.implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
            // Annotation processor
            extension.kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
            // alternately - if using Java8, use the following instead of lifecycle-compiler
            extension.implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

            // optional - helpers for implementing LifecycleOwner in a Service
            extension.implementation "androidx.lifecycle:lifecycle-service:$lifecycle_version"

            // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
            extension.implementation "androidx.lifecycle:lifecycle-process:$lifecycle_version"

            // optional - ReactiveStreams support for LiveData
            extension.implementation "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"

            // optional - Test helpers for LiveData
            extension.testImplementation "androidx.arch.core:core-testing:$arch_version"

            // optional - Test helpers for Lifecycle runtime
            extension.testImplementation "androidx.lifecycle:lifecycle-runtime-testing:$lifecycle_version"
            extension.kapt 'com.alibaba:arouter-compiler:1.5.2'

            extension.implementation 'androidx.databinding:library:3.2.0-alpha11'
            if (appImplementation != "common") {
                //common做为中间层，所有的Module都要依赖
                extension.implementation extension.project(path: ':common')
            }
            //针对每个Module单独进行依赖
            switch (appImplementation) {
                case "app":
                    extension.implementation extension.project(path: ':account')
                    extension.implementation extension.project(path: ':code')
                    break
                case "account":

                    break
                case "common"://common组件是一个中间层，所有的组件都需要依赖此组件，公共的依赖便可放到这里
                    extension.api 'com.alibaba:arouter-api:1.5.2'//ARouter依赖
                    //datastore 存储数据，仅用于测试
                    extension.implementation "androidx.datastore:datastore-preferences:1.0.0"
                    //gson，仅用于测试
                    extension.api 'com.google.code.gson:gson:2.8.5'
                    break
            }
    }
}
```

主模块的build.gradle设置为

```css
apply from: "${rootProject.rootDir}/assembly.gradle"
project.ext.setAppDefaultConfig project
```

其他模块的build.gradle设置为

```css
apply from: "${rootProject.rootDir}/assembly.gradle"
project.ext.setAppOrLibDefaultConfig project
```

## 修改清单文件

![image-20230331144100683](https://cdn.jsdelivr.net/gh/QminCode/image@main/image-20230331144100683.png)

在模块下新建manifest文件夹，如上图所示，复制一份清单文件。

需要注意，manifest文件下的AndroidManifest.xml文件和组件下的AndroidManifest.xml文件是有区别的，区别就是，当你选择组件运行时，会编译manifest文件下的，当做依赖使用时，走组件下的，除此之外，当你选择组件运行时，manifest文件下的AndroidManifest.xml文件必须有主入口，如下：

![image-20230331144400742](https://cdn.jsdelivr.net/gh/QminCode/image@main/image-20230331144400742.png)

组件下的AndroidManifest.xml文件不需要有主入口

![image-20230331144731750](https://cdn.jsdelivr.net/gh/QminCode/image@main/image-20230331144731750.png)

## 创建APP类

![image-20230331153003747](https://cdn.jsdelivr.net/gh/QminCode/image@main/image-20230331153003747.png)
