# NetGuard Core ProGuard Rules
# These rules are applied when a consumer app uses R8/ProGuard

# Keep public API
-keep class io.github.shaharzohar.netguard.core.NetGuard { *; }
-keep class io.github.shaharzohar.netguard.core.config.** { *; }
-keep class io.github.shaharzohar.netguard.core.logging.** { *; }
-keep class io.github.shaharzohar.netguard.core.models.** { *; }

# Keep enums
-keepclassmembers enum io.github.shaharzohar.netguard.core.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
