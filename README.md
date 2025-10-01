<div align="center">

<img src="composeApp/src/commonMain/composeResources/drawable/ic_launcher.png" alt="OrganizePlus Logo" width="120" height="120">

# 📋 OrganizePlus

**A modern duty and task management app built with Kotlin Multiplatform and Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.9.0-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-24%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![iOS](https://img.shields.io/badge/iOS-13%2B-000000?style=for-the-badge&logo=ios&logoColor=white)](https://developer.apple.com/ios/)

[![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=for-the-badge)](http://makeapullrequest.com)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg?style=for-the-badge)](https://github.com/joffer/organizeplus/graphs/commit-activity)

</div>

---

## 🚀 Features

### ✨ Core Functionality
- **📝 Duty Management** - Create, edit, and track your duties and obligations
- **📊 Dashboard Overview** - Get a comprehensive view of your tasks and progress
- **📅 Smart Scheduling** - Set start dates, due dates, and reminders
- **🏷️ Categorization** - Organize duties by categories (Personal, Business, etc.)
- **⚡ Priority Levels** - Mark tasks as Low, Medium, High, or Urgent
- **📱 Multi-Platform** - Native experience on Android and iOS

### 🎨 Modern UI/UX
- **Material Design 3** - Beautiful, modern interface following Google's design guidelines
- **Dark/Light Theme** - Automatic theme switching based on system preferences
- **Responsive Design** - Optimized for all screen sizes
- **Smooth Animations** - Delightful micro-interactions and transitions

### 🔧 Technical Features
- **Kotlin Multiplatform** - Share business logic across platforms
- **Jetpack Compose** - Modern declarative UI framework
- **MVVM Architecture** - Clean, maintainable code structure
- **Dependency Injection** - Koin for modular dependency management
- **Local Database** - SQLDelight for efficient data persistence
- **Reactive Programming** - Kotlin Flows for reactive data streams

---

## 📱 Screenshots

<div align="center">
  <img src="https://via.placeholder.com/300x600/4285F4/FFFFFF?text=Dashboard" alt="Dashboard" width="200"/>
  <img src="https://via.placeholder.com/300x600/34A853/FFFFFF?text=Create+Duty" alt="Create Duty" width="200"/>
  <img src="https://via.placeholder.com/300x600/FF6B6B/FFFFFF?text=Duty+List" alt="Duty List" width="200"/>
  <img src="https://via.placeholder.com/300x600/9C27B0/FFFFFF?text=Settings" alt="Settings" width="200"/>
</div>

---

## 🛠️ Tech Stack

### **Frontend**
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.9.0-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Material Design 3](https://img.shields.io/badge/Material_Design_3-3.0-757575?style=flat-square&logo=materialdesign&logoColor=white)
![Compose Navigation](https://img.shields.io/badge/Compose_Navigation-2.8.4-4285F4?style=flat-square&logo=android&logoColor=white)

### **Backend & Data**
![SQLDelight](https://img.shields.io/badge/SQLDelight-2.0.2-009688?style=flat-square&logo=sqlite&logoColor=white)
![Room](https://img.shields.io/badge/Room-2.8.0-009688?style=flat-square&logo=android&logoColor=white)
![Kotlinx Serialization](https://img.shields.io/badge/Kotlinx_Serialization-1.7.3-7F52FF?style=flat-square&logo=kotlin&logoColor=white)

### **Architecture & DI**
![Koin](https://img.shields.io/badge/Koin-4.0.1-FF6B6B?style=flat-square&logo=android&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-FF9800?style=flat-square&logo=android&logoColor=white)
![Kotlin Flows](https://img.shields.io/badge/Kotlin_Flows-1.10.2-7F52FF?style=flat-square&logo=kotlin&logoColor=white)

### **Networking**
![Ktor](https://img.shields.io/badge/Ktor-3.0.1-009688?style=flat-square&logo=ktor&logoColor=white)
![OkHttp](https://img.shields.io/badge/OkHttp-3.0.1-009688?style=flat-square&logo=android&logoColor=white)

### **Development Tools**
![Detekt](https://img.shields.io/badge/Detekt-1.23.6-FF6B6B?style=flat-square&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.11.2-02303A?style=flat-square&logo=gradle&logoColor=white)

---

## 🏗️ Project Structure

```
OrganizePlus/
├── 📱 composeApp/                    # Main application module
│   ├── 🎨 commonMain/               # Shared code across platforms
│   │   ├── 🎯 features/             # Feature modules
│   │   │   ├── 📊 dashboard/        # Dashboard feature
│   │   │   ├── 📝 duty/             # Duty management module
│   │   │   │   ├── ✏️ create/        # Create duty feature
│   │   │   │   ├── 📋 list/         # Duty listing feature
│   │   │   │   ├── 📄 detail/       # Duty details feature
│   │   │   │   └── 📅 occurrence/   # Duty occurrence tracking
│   │   │   ├── ⚙️ settings/         # Settings
│   │   ├── 🎨 designsystem/         # Design system components
│   │   ├── 🗄️ database/             # Database layer
│   │   ├── 🧭 navigation/           # Navigation logic
│   │   └── 🔧 di/                   # Dependency injection
│   ├── 🤖 androidMain/              # Android-specific code
│   ├── 🍎 iosMain/                  # iOS-specific code
│   └── 🌐 jsMain/                   # Web-specific code
├── 🍎 iosApp/                       # iOS app wrapper
└── 📦 core/                         # Core shared module
```

---

## 📋 Project Rules

### 🚫 No Hardcoded Strings

**CRITICAL RULE**: Never use hardcoded strings in the codebase. All user-facing text must be defined in string resources.

- ✅ **Correct**: `stringResource(Res.string.settings_title)`
- ❌ **Wrong**: `"Settings"`

See [PROJECT_RULES.md](PROJECT_RULES.md) for detailed guidelines and enforcement tools.

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (latest version)
- **Xcode** (for iOS development)
- **JDK 11** or higher
- **Kotlin** 2.2.20+
- **Gradle** 8.11.2+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/joffer/organizeplus.git
   cd organizeplus
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder and select it

3. **Sync the project**
   - Android Studio will automatically sync the project
   - Wait for the sync to complete

### Running the App

#### 🤖 Android
```bash
./gradlew :composeApp:assembleDebug
```

#### 🍎 iOS
1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select your target device/simulator
3. Click the Run button

#### 🌐 Web
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

#### 🖥️ Desktop (JVM)
```bash
./gradlew :composeApp:run
```

---

## 📖 Usage

### Creating a New Duty
1. Open the app and navigate to the Dashboard
2. Tap the **"+ New Duty"** button
3. Fill in the duty details:
   - **Title**: Enter a descriptive title
   - **Start Date**: When the duty begins
   - **Due Date**: When the duty should be completed
   - **Category**: Choose from Personal or Business
   - **Priority**: Set the urgency level
   - **Reminders**: Configure notification settings
4. Tap **"Save"** to create the duty

### Managing Duties
- **Dashboard**: View all duties at a glance
- **Duty List**: See all duties in a detailed list
- **Duty Details**: Edit or view specific duty information
- **Settings**: Customize app preferences

---

## 🏛️ Architecture

OrganizePlus follows **MVVM (Model-View-ViewModel)** architecture with clean separation of concerns:

### **Presentation Layer**
- **Screens**: Compose UI components
- **ViewModels**: Handle UI state and business logic
- **Intents**: User actions and events

### **Domain Layer**
- **Use Cases**: Business logic implementation
- **Entities**: Core business models
- **Repositories**: Data access interfaces

### **Data Layer**
- **Repositories**: Data access implementation
- **Database**: SQLDelight for local storage
- **Network**: Ktor for API communication

---

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run Android tests
./gradlew :composeApp:testDebugUnitTest

# Run iOS tests
./gradlew :composeApp:iosSimulatorArm64Test
```

---

## 📦 Dependencies

### **Core Libraries**
- **Kotlin Multiplatform** - Cross-platform development
- **Jetpack Compose** - Modern UI toolkit
- **Koin** - Dependency injection
- **SQLDelight** - Type-safe SQL database
- **Ktor** - HTTP client
- **Kotlinx Serialization** - JSON serialization

### **UI Libraries**
- **Material Design 3** - Design system
- **Compose Navigation** - Navigation component
- **Coil** - Image loading
- **Kamel** - Image loading for Compose

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### **Development Guidelines**
- Follow Kotlin coding conventions
- Write unit tests for new features
- Update documentation as needed
- Use conventional commit messages

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **JetBrains** for Kotlin Multiplatform and Compose
- **Google** for Material Design 3
- **Open source community** for amazing libraries and tools

---

## 📞 Support

If you have any questions or need help:

- 📧 **Email**: support@organizeplus.app
- 🐛 **Issues**: [GitHub Issues](https://github.com/joffer/organizeplus/issues)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/joffer/organizeplus/discussions)

---

<div align="center">

**Made with ❤️ using Kotlin Multiplatform**

[![GitHub stars](https://img.shields.io/github/stars/joffer/organizeplus?style=social)](https://github.com/joffer/organizeplus/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/joffer/organizeplus?style=social)](https://github.com/joffer/organizeplus/network)
[![GitHub watchers](https://img.shields.io/github/watchers/joffer/organizeplus?style=social)](https://github.com/joffer/organizeplus/watchers)

</div>