# NetGuard Captive Portal ProGuard Rules

-keep class io.github.shaharzohar.netguard.captiveportal.detector.** { *; }
-keep class io.github.shaharzohar.netguard.captiveportal.wispr.** { *; }
-keep class io.github.shaharzohar.netguard.captiveportal.dns.** { *; }
-keep class io.github.shaharzohar.netguard.captiveportal.models.** { *; }

# Keep enums
-keepclassmembers enum io.github.shaharzohar.netguard.captiveportal.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
