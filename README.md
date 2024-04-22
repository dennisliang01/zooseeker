# ZooSeeker Android App

ZooSeeker is an Android application developed by Team 52 for navigating zoo exhibits efficiently. It allows users to create visitation plans, search for exhibits, and get directions based on real-time location within the zoo.

## Features

- **Plan Visits:** Users can create and modify their visitation plans.
- **Search for Exhibits:** Allows searching through an extensive database of zoo exhibits.
- **Navigation:** Provides step-by-step directions to navigate through the zoo.
- **Location Awareness:** Utilizes real-time location data to improve navigation.

## Installation

To install ZooSeeker, follow these steps:

1. Clone the repository.
2. Open the project in Android Studio.
3. Build the project and run on an Android device or emulator.

## Usage

Once installed, start the app and follow the on-screen instructions to select exhibits, create a plan, and navigate through the zoo.

## Folder structure

```bash
ZooSeeker/
├── app/
│   ├── src/
│   │   ├── androidTest/                # Contains Android instrumented tests.
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── zooseeker_jj_zaaz_team_52/   # Test cases for the ZooSeeker app functionalities.
│   │   ├── main/                       # Core source files for the app.
│   │   │   ├── AndroidManifest.xml     # Configures essential settings and permissions for the app.
│   │   │   ├── assets/                 # Static files such as JSON data for the zoo layouts and exhibits.
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── zooseeker_jj_zaaz_team_52/   # Java source files for main app functionalities.
│   │   │   └── res/                    # UI resources, including layouts, strings, and images for different screen densities.
│   │   └── test/                       # Unit tests for Java classes.
│   │       └── java/
│   │           └── com/
│   │               └── example/
│   │                   └── zooseeker_jj_zaaz_team_52/   # Unit tests for non-UI components.
│   ├── build.gradle                    # App-specific Gradle build configuration.
│   └── proguard-rules.pro              # Rules for shrinking and obfuscation of the code.
├── gradle/                             # Contains Gradle wrapper, enabling consistent build environments.
│   └── wrapper/
│       ├── gradle-wrapper.jar          # The wrapper itself, a binary JAR file.
│       └── gradle-wrapper.properties   # Properties for the wrapper, specifying Gradle distribution.
├── gradlew                             # Unix shell script to run the Gradle task.
├── gradlew.bat                         # Batch script for running Gradle task on Windows.
├── local.properties                    # Specifies local environment settings, typically SDK paths.
├── settings.gradle                     # Root settings for the Gradle build system.
└── build.gradle                        # Top-level Gradle build script, typically for defining plugins and versions.
```

## Testing

The app includes unit tests and instrumental tests to ensure functionality:

- **Unit Tests:** Test individual components.
- **Instrumental Tests:** Ensure the app behaves correctly with Android hardware.

To run the tests, use the following command in Android Studio:
`./gradlew test`
