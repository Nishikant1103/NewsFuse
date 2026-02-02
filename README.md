# NewsFuse

NewsFuse is an Android application that aggregates and displays news articles from various sources. It is built using Kotlin, Jetpack Compose, Room, WorkManager, and modern Android architecture components. The app features offline support, periodic background news syncing, and a clean, responsive UI.

## Features
- Fetches news from RSS/Atom feeds
- Displays news list and details
- Offline support using Room database
- Periodic background sync with WorkManager
- Network connectivity status monitoring
- Type-safe navigation with Jetpack Compose
- Modern UI with Material Design

## Architecture
- **MVVM** pattern using ViewModel and Repository
- **Dependency Injection** with Hilt
- **Room** for local database caching
- **WorkManager** for background sync
- **LiveData/Flow** for reactive UI updates

## Getting Started
### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- Gradle 8.0+

### Build & Run
1. Clone the repository:
   ```sh
   git clone <repo-url>
   ```
2. Open in Android Studio.
3. Sync Gradle and build the project.
4. Run on an emulator or device (Android 8.0+).

### Main Modules
- `app/` - Main application module
- `datasource/` - Data models, local DB, network sources
- `view/` - Compose UI screens
- `core/` - DI, application class, utilities
- `workers/` - WorkManager background workers

## Usage
- Launch the app to view the latest news.
- Tap a news item to view details.
- Pull to refresh or wait for periodic sync.
- Offline articles are available if previously fetched.

## Dependencies
- Jetpack Compose
- Room
- WorkManager
- Hilt
- Coil (image loading)
- Lottie (animations)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

## Contact
For support or inquiries, contact the maintainer at <your-email>.

