# Visual Map Context and Problem Statement

The zoo app aims to enhance the visitor experience by providing a digital visual map that aids navigation, exhibits information, and real-time updates. The choice of design for the visual map is critical to ensure usability, accessibility, and engagement, which we have collectively agreed upon in the planning meeting.

## Decision Drivers

- **User Experience:** The map must be intuitive and easy to navigate for users of all ages. Like children who would like more visually.
- **Accessibility:** The map should be accessible to visitors with disabilities.
- **Performance:** The map should load quickly and work smoothly across different devices.
- **Maintainability:** The map should be easy to update with new exhibits and changes in the zoo layout.

## Considered Options

### Static Image Map with Dynamic Marker

- **Pros:** Simple to implement, low maintenance.
- **Cons:** Difficult to update, not as engaging.

### Interactive SVG Map

- **Pros:** Scalable without losing quality, interactive, easily updated.
- **Cons:** Requires more initial development effort, may require more advanced skills to maintain.

### Google Maps Integration

- **Pros:** Familiar interface, reliable navigation features, real-time updates.
- **Cons:** Limited customization, dependent on external service, may not align with zoo branding.

## Decision Outcome

**Chosen Option:** Static Image Map

### Rationale

- **User Experience:** The static map offers an engaging and intuitive user experience with features like pan, and clickable areas for detailed information about exhibits.
- **Ease of Implementation:** It is relatively easy to implement and get to working quickly, which is critical since other team members will depend on this feature.
- **Integration:** The interactive static map can be easily integrated with other app features and styled to match the zoo’s branding.

## Implications

- **Development:** Requires investment in manually getting location data from the static map as well as scaling the marker based on the exhibit area.
- **Testing:** Extensive testing across different devices and browsers is necessary to ensure consistent performance and accessibility.

## Status

**Approved**

## Consequences

The choice of a static map provides a fine balance between feasibility, mostly the total time needed for completing with a high-quality, engaging, and accessible user experience, aligning with the zoo app's goals of enhancing visitor satisfaction and operational efficiency.
