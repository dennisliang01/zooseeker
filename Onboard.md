# Powell Zoo App Onboarding

Welcome to Team 6's Powell Zoo App! We're excited to have you on board. This document will help you get started and provide you with key information about our project.

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [Tools and Resources](#tools-and-resources)
4. [FAQ](#faq)
5. [Folder Structure](#folder-structure)

## Introduction

ZooSeeker is an Android application whose purpose is to help new visitors to the San Diego zoo navigate to exhibits efficiently. It allows users to create visitation plans, search for exhibits, and get directions based on real-time location within the zoo, facilitated by a map-style interface that allows users to plot actionable paths to the exhibits they want to see.

## Getting Started

To install ZooSeeker, follow these steps:

1. Clone the repository.
2. Select open project and select the folder you just cloned in Android Studio.
3. Go to Tools -> Device Manager, there you will configure and download the virtual device that you need to run the app on Android, select any phone you want and just selecting the recommended system image should be ok.
4. Wait for the gradle files to finish compiling, after you should see a run configuration named "app", and next to it in the available devices select the simulator you set up in the previous step.
5. Click the "Run app" button and Android Studio should build and startup the simulator with the app installed.

## Tools and Resources

### General tools

- Android Studio (IDE for Android app development)
- Gradle (Build automation tool)
- Git (Version control system)
- GitHub (Code hosting platform)

### Dependency List

- **'com.google.android.material:material:1.12.0'**: Material Components for Android, including design support for buttons, cards, etc.

- **'androidx.test:core:1.4.0'**: Core testing library for AndroidX.

- **'androidx.navigation:navigation-fragment:2.6.0'** and **'androidx.navigation:navigation-ui:2.6.0'**: Android Jetpack Navigation components for fragment navigation and UI-related navigation.

- **'com.github.bumptech.glide:glide:4.12.0'**: Image loading and caching library for Android.

- **'com.google.code.gson:gson:2.9.0'**: Gson library for converting Java objects to JSON and vice versa.

- **'androidx.room:room-runtime:2.4.2'**: Android Jetpack's Room persistence library for SQLite database interactions.

## FAQ

### Q: What technologies and libraries are used in the ZooSeeker project?

A: Refer to [Tools and Resources](#tools-and-resources).

### Q: Even though I can install the application correctly, when I try to run the application on an emulator I get an error regarding a version mismatch between either gradle or another dependency. How can I fix this?

A: For the above problem, try any of the below fixes:

1. In the Android Studio menu, click on Build > Clean Project, then attempt to rebuild the project and re-run the emulator
2. In the Android Studio menu, click on File > Invalidate Caches and Restart, then then attempt to re-run the emulator
3. In the Project directory, make an edit to the project `build.gradle` file (not the `app/build.gradle` file) and then immediately revert that edit. A prompt should appear in the Android Studio console prompting to resync Gradle, click the affirmative option, then then attempt to re-run the emulator

### Q: How is data stored during the application's runtime?

A: The current iteration of the application achieves all of its data storage locally, relying on local files to populate information related to each animal exhibit, as well as to house the geographical and navigational information relevant to the creation of each unique visitation plan and shortest optimal path through said exhibits. A standard singleton pattern is used to store and persist data between different fragments in the application, implemented using the Android Room database functionality to house all relevant fields.

### Q: The Settings page of the application looks comparatively bare. Why is this?

A: In its current iteration, the project does not meaningfully leverage the settings page for any other purpose than filtering out nodes in its Map page. The deployment of a minimum viable version of the application is expected to be supplemented with additional settings options, particularly ones that include togglable accessibility options and user profile functionalities.

### Q: What glaring bugs might a user experience when using the application for its intended purpose?

A: At the moment, no prominent bugs have been detected in the application's map navigation, plan aggregation, or settings selection features which might meaningfully inhibit a user's ability to utilize the application.

## Folder structure

```bash
ZooSeeker/
├── app/
│   ├── src/
│   │   ├── androidTest/ # Espresso UI Tests
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── zooseeker_jj_zaaz_team_52/
│   │   │                   ├── AddFromMapDetailsTest.java
│   │   │                   ├── AddToPlanFromMapTest.java
│   │   │                   └── MapTest.java
│   │   ├── test/ # Unit Tests
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── zooseeker_jj_zaaz_team_52/
│   │   │                   ├── ActivityDatabaseTest.java
│   │   │                   ├── ExhibitSearchTest.java
│   │   │                   ├── SkipExhibitTest.java
│   │   │                   ├── ZooNavigatorTest.java
│   │   │                   └── PlanDatabaseTest.java
│   │   ├── main/
│   │   │   ├── res/
│   │   │   │   ├── drawable/ # Includes images of animals
│   │   │   │   ├── layout/ # Include layout files for pages and components
│   │   │   │   ├── values/ # Includes static & constants
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   ├── styles.xml
│   │   │   │   │   └── strings.xml
│   │   │   │   ├── navigation/ # Navigation-related layout files
│   │   │   │   │   ├── mobile_navigation.xml
│   │   │   │   │   └── nav_graph.xml
│   │   │   │   └── menu/ # Navigation-related layout  files
│   │   │   │       ├── bottom_nav_menu.xml
│   │   │   │       ├── settings_menu.xml
│   │   │   │       └── search_menu.xml
│   │   │   ├── AndroidManifest.xml
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── zooseeker_jj_zaaz_team_52/
│   │   │                   ├── ExhibitSearch.java
│   │   │                   ├── ZooNavigator.java
│   │   │                   ├── MainActivity.java
│   │   │                   └── ui/
│   │   │                       ├── Map/ # UI components for Map tab
│   │   │                       │   ├── MapViewModel.java
│   │   │                       │   ├── DetailsFragment.java
│   │   │                       │   └── MapFragment.java
│   │   │                       ├── Plan/ # UI components for Plan tab
│   │   │                       │   ├── PlanFragment.java
│   │   │                       │   ├── SearchShowAdapter.java
│   │   │                       │   ├── DirectionFragment.java
│   │   │                       │   └── SearchListViewModel.java
│   │   │                       └── Setting/ # UI components for Settings tab
│   │   │                           ├── SettingViewModel.java
│   │   │                           └── SettingFragment.java
│   │   └── assets/
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
