# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/tommy/android/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#Proguard基本语法
# -keep 不混淆类和成员或者被重命名
#
# -keepnames 防止类和成员被重命名
#
# -keepclassmembers 不混淆成员被移除或者被重命名
#
# -keepnames 防止成员被重命名
#
# -keepclasseswithmembers 不混淆拥有该成员的类和成员或者被重命名
#
# -keepclasseswithmembernames 防止拥有该成员的类和成员被重命名
#
# 而且还支持通配符*例如不混淆某个类
#
# -keep public class com.shadow.example.abc { *; }
#
# 不混淆某个包
#
# -keep public class com.shadow.example.** { *; }
#
# 不混淆某个类的子类
#
# -keep public class * extends com.shadow.exampl.abc { *; }

#--- 基础混淆配置 ---
-optimizationpasses 5 #指定代码的压缩级别

-allowaccessmodification #优化时允许访问并修改有修饰符的类和类的成员

-dontusemixedcaseclassnames #不使用大小写混合

-dontskipnonpubliclibraryclasses #指定不去忽略非公共库的类

-verbose #混淆时是否记录日志

-ignorewarnings #忽略警告，避免打包时某些警告出现，没有这个的话，构建报错

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/* #混淆时所采用的算法

-keepattributes *Annotation* #不混淆注解相关

-keepclasseswithmembernames class * { #保持 native 方法不被混淆
native <methods>;
}

-keepclassmembers enum * { #保持枚举 enum 类不被混淆
public static **[] values();
public static ** valueOf(java.lang.String);
}

#不混淆Parcelable
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

#不混淆Serializable
-keep class * implements java.io.Serializable {*;}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {*;}

-keepclassmembers class **.R$* { #不混淆R文件
    public static <fields>;
}

#不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

-keepattributes Signature #过滤泛型 出现类型转换错误时，启用这个

#--- 不能被混淆的基类 ---
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class org.xmlpull.v1.** { *; }

#--- 不混淆android-support-v4包 ---
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class * extends android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.widget
-keep class * extends android.support.v4.app.** {*;}
-keep class * extends android.support.v4.view.** {*;}
-keep public class * extends android.support.v4.app.Fragment

#不混淆继承的support类
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

#不混淆log
-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int v(...);
public static int i(...);
public static int w(...);
public static int d(...);
public static int e(...);
}

#保持Activity中参数类型为View的所有方法
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

#不混淆我们自定义控件（继承自View）
-keep public class * extends android.view.View{
*** get*();
void set*(***);
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
}

## 不混淆自定义的包
##
-keep public class  cn.ifhu.supplier.view.** { *; }

-keep public class * extends android.widget.TextView{
*** get*();
void set*(***);
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
}

#android-arch-core
-dontwarn android.arch.**
-keep class android.arch.** {*;}


#极光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}

-dontwarn cn.sharesdk.**
-dontwarn **.R$*

#bugsnag
-dontwarn com.bugsnag.**
-keep class com.bugsnag.**{*;}


#play-services-gcm
-dontwarn com.google.**
-keep class com.google.** {*;}


#com.intercom
-dontwarn com.intercom.**
-keep class com.intercom.** {*;}

#mixpanel
-keep class com.mixpanel.android.abtesting.** { *; }
-keep class com.mixpanel.android.mpmetrics.** { *; }
-keep class com.mixpanel.android.surveys.** { *; }
-keep class com.mixpanel.android.util.** { *; }
-keep class com.mixpanel.android.java_websocket.** { *; }

-keepattributes InnerClasses

-keep class **.R
-keep class **.R$* {
    <fields>;
}

#MobSDK
-keep class com.mob.**{*;}
-keep class cn.smssdk.**{*;}

-dontwarn com.mob.**
-dontwarn cn.smssdk.**


#javawriter
-dontwarn com.squareup.javawriter.JavaWriter


#io.intercom
-dontwarn io.intercom.android.**
-keep class io.intercom.** {*;}

#javax
-dontwarn javax.annotation.**
-dontwarn javax.inject.**


#SQLCipher
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.** {*;}

#Event Bus
-keepclassmembers class ** {
    public void onEvent*(***);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}

# Don't warn for missing support classes
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

#greendao3.2.2,此是针对3.2.2，如果是之前的，可能需要更换下包名
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {public static java.lang.String TABLENAME;}
-keep class **$Properties

# hamcrest-core
-keep class org.hamcrest.**{*;}
-dontwarn org.hamcrest.**

#Android-gif-drawable
-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}

-keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}

#newrilic
-keep class com.newrelic.** { *; }
-dontwarn com.newrelic.**
-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable

-keep class top.zibin.luban.**
-keep class com.facebook.**

-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionYes <methods>;
}
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionNo <methods>;
}

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# Gson
-keep class cn.ifhu.supplier.model.bean.**{*;} # 自定义数据模型的bean目录


# glide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#图片选择
-dontwarn com.squareup.picasso.**
#权限申请
-dontwarn com.yanzhenjie.permission.**



# ProGuard configurations for Bugtags
  -keepattributes LineNumberTable,SourceFile

  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient

  # End Bugtags

-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}