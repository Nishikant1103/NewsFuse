# NewsFuse
This is the repository for the News Fuse app. It is a work in progress 🚧.
NewsFuse is an Android application that aggregates and displays news articles from various rss feed sources. It is built using Kotlin, Jetpack Compose, Room, WorkManager, and modern Android architecture components. The app features offline support, periodic background news syncing, and a clean, responsive UI.

## Features
The News Fuse app has following feature:
1. The news list screen:
- Displays a list of news articles with title, source name alias and post date from the selected rss feed source.
![](/Users/nishikant.choudhary/Desktop/Screenshot 2026-02-17 at 18.43.48.png)
2. The news details screen:
- When clicked on a news item the user navigates to the news details screen which displays the news title, description, image(if available), post date and an link to the source article.
![](/Users/nishikant.choudhary/Desktop/Screenshot 2026-02-17 at 18.45.51.png)
3. News feeds management screen:
- The user can add, delete and select news feed sources. 
- Each news feed source has a name alias and a rss feed url. 
- The user can also select only one news feed source at this point to view its news articles.
- There is no possibility to edit a news feed source at this point.
- In case user tries to add an invalid rss feed url, an error message is shown to the user.
![](/Users/nishikant.choudhary/Desktop/Screenshot 2026-02-17 at 18.45.14.png)

![](/Users/nishikant.choudhary/Desktop/Screenshot 2026-02-17 at 18.45.01.png)

Additional features include:
- Offline support using Room database
- Periodic background sync with WorkManager
- Network connectivity status monitoring
- Type-safe navigation with Jetpack Compose
- Modern UI with Material Design

## Steps to add a new news feed source
1. Open the app and navigate to the news feed management screen by clicking on the feeds icon in the top right corner of the news list screen.
2. Click on the floating action button to open the add feed screen.
3. Enter a name alias for the news feed source in the "Name" field.
4. Enter the rss feed url for the news feed source in the "URL" field.
5. Click on the "Add feed" button to add the news feed source.
6. If the rss feed url is valid, the news feed source will be added to the list and you can swipe right the added feed to select it to view its news articles. If the rss feed url is invalid, an error message will be shown and the news feed source will not be added.

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
   git clone https://github.com/Nishikant1103/NewsFuse.git
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

### Supported Languages
- English (en)
- German (de)

## Usage
- Launch the app to view the latest news.
- Tap a news item to view details.
- Pull to refresh or wait for periodic sync.
- Offline articles are available if previously fetched.

## Dependencies
- Jetpack Compose
- Room
- WorkManager
- Coil (image loading)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

## Contact
For support or inquiries, contact the maintainer at nishikant.choudhary@outlook.com .

