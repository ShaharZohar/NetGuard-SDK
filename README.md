# NetGuard Developer SDK

[![CI](https://github.com/ShaharZohar/netguard-sdk/workflows/CI/badge.svg)](https://github.com/ShaharZohar/netguard-sdk/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.shaharzohar/netguard-core.svg)](https://search.maven.org/artifact/io.github.shaharzohar/netguard-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

**NetGuard** is an open-source Android SDK for network diagnostics, captive portal detection, traffic logging, and WiFi monitoring. Built for developers and security researchers who need deep visibility into network behavior.

## Features

- 🔍 **Captive Portal Detection** - Multi-technique detection using NetworkCapabilities API, HTTP probing, and WISPr protocol parsing
- 🚫 **DNS Hijacking Detection** - Verify DNS resolution integrity against known addresses
- 📊 **Traffic Logging** - Full HTTP request/response capture with OkHttp interceptor
- 📶 **WiFi Monitoring** - Real-time signal strength, connection info, and network quality metrics
- 🔐 **Auto-Authentication** - WISPr protocol support for automatic captive portal login
- 🧹 **Zero Production Overhead** - No-op variants for release builds



## Permissions

```xml
<!-- Required -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- For WiFi monitoring -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

## ProGuard

The SDK includes consumer ProGuard rules. No additional configuration needed.

## API Documentation

Full API documentation is available at [shaharzohar.github.io/netguard-sdk](https://shaharzohar.github.io/netguard-sdk)

## Requirements

- **Minimum SDK**: 24 (Android 7.0)
- **Compile SDK**: 34 (Android 14)
- **Kotlin**: 1.9+
- **Java**: 17

## Contributing

Contributions are welcome! 


## License

```
Copyright 2025 Shahar Zohar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Author

**Shahar Zohar** - Senior Full Stack Developer
- GitHub: [@ShaharZohar](https://github.com/ShaharZohar)
- LinkedIn: [shahar-zohar-b8096649](https://linkedin.com/in/shahar-zohar-b8096649)
