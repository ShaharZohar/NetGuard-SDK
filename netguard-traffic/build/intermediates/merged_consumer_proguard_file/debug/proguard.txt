# NetGuard Traffic ProGuard Rules

-keep class io.github.shaharzohar.netguard.traffic.interceptor.** { *; }
-keep class io.github.shaharzohar.netguard.traffic.models.** { *; }
-keep class io.github.shaharzohar.netguard.traffic.storage.** { *; }
-keep class io.github.shaharzohar.netguard.traffic.ui.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
