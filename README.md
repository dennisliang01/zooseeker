# ZooSeeker Android App

ZooSeeker is an Android application whose purpose is to help new visitors to the San Diego zoo navigate to exhibits efficiently. It allows users to create visitation plans, search for exhibits, and get directions based on real-time location within the zoo, facilitated by a map-style interface that allows users to plot actionable paths to the exhibits they want to see.

## Features

- **Plan Visits:** Users can create and modify their visitation plans.
- **Search for Exhibits:** Allows searching through an extensive database of zoo exhibits.
- **Navigation:** Provides step-by-step directions to navigate through the zoo.
- **Location Awareness:** Utilizes real-time location data to improve navigation.

## Usage

Once installed, start the app and follow the on-screen instructions to select exhibits, create a plan, and navigate through the zoo.

## Documentation

[Settings ADR](./Project%20Management/ADR/Database%20ADR)

[Database ADR](./Project%20Management/ADR/Database%20ADR)

[UI ADR](./Project%20Management/ADR/UI_ADR.md)

[Biweekly Workplans](./Project%20Management/CSE112%20Workplans)

[Onboarding Guide](./Onboard.md)

## Testing

The app includes unit tests and instrumental tests to ensure functionality:

- **Unit Tests:** Test individual components.
- **Instrumental Tests:** Ensure the app behaves correctly with Android hardware.

To run the tests, use the following command in Android Studio:
`./gradlew test`
