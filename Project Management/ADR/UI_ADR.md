<h2 style="color:#555555;">Direction of Exhibits and Schedule Context and Problem Statement</h2>
<p style="border-top: 3px solid lightgray;"></p>

<p style="color:#555555;">The zoo aims to enhance user experience streamlining the workflow and improve user interface with extra interactive features.</p>

<h3 style="color:#555555;">Decision Drivers</h3>
<p style="border-top: 2px solid lightgray;"></p>
<ul>
    <li style="color:#555555;"><b >User Experience:</b> The plan and direction interfaces must be engaging and easy to navigate for users of all ages.</li>
    <li style="color:#555555;"><b >Assessibility:</b> All user should be able to plan a schedule and seek directions to any exhibit.</li>
    <li style="color:#555555;"><b >Performance:</b> The plan and direction interfaces should load quickly and run smoothly across different devices and various sizes of data.</li>
    <li style="color:#555555;"><b >Maintainability:</b> The plan should be accessible to update exhibits and exhibits' locations.</li>
</ul>

<h3 style="color:#555555;">Chosen Options</h3>
<p style="border-top: 2px solid lightgray;"></p>

<h4 style="color:#555555;">View Plan Removal</h4>
<ul>
    <li style="color:#555555;"><b>Pros:</b> Reducing extra fragment, simple implementation, low maintenance, increase action automation (create plan and generate directions).</li>
    <li style="color:#555555;"><b>Cons:</b> User cannot see the entire schedule along with the distance for each exhibit in a single screen.</li>
</ul>

<h4 style="color:#555555;">Button Icon Revamps</h4>
<ul>
    <li style="color:#555555;"><b>Pros:</b> Friendly for user with vision problem and adding additional end plan feature and easy to implement.</li>
    <li style="color:#555555;"><b>Cons:</b> The icons are space intensive and the icons are a bit confusing to understand.</li>
</ul>

<h4 style="color:#555555;">Progress Bar</h4>
<ul>
    <li style="color:#555555;"><b>Pros:</b> Keeping track of the user's progress on their schedule and low maintenance. Addionally enhance user experience</li>
    <li style="color:#555555;"><b>Cons:</b> Requires development effort initially to sync progress bar with current exhibit on the schedule.</li>
</ul>

**Green Theme**
- **Pros**: Consistency with Theme: The green color scheme is consistent with the natural theme of a zoo. Visual Appeal: Green is associated with nature, which aligns well with our app's purpose.
- **Cons**: Color Limitation: Using predominantly green might limit the app's visual variety.

**Exhibit Detail Page**
- **Pros**: Intuitive Layout: Centering the animal image and using icons makes the exhibit detail page easy to understand. Informative: Helps users get a good sense of what the animal looks like and provides detailed descriptions for those who want more information.
- **Cons**: Complexity in Design: Centering images and including various icons and descriptions can make the design process more complex.

**Planning Page**
- **Pros**: Clean Design: Circular icons for the animal list provide a clean and organized look. Intuitive: Provides good intuition about the animals included in the lists.
- **Cons**: Icon Limitation: Circular icons may limit the space for other information.

**Search Page**
- **Pros**: Enhanced Usability: Recommendations and images on the search page improve user experience. User-Friendly: Allows users to search for an animal even if they do not know its name.
- **Cons**: Implementation Effort: Adding images and recommendations requires additional development effort.
   
<h3 style="color:#555555;">Decision Outcomes</h3>
<p style="border-top: 2px solid lightgray;"></p>


<h4 style="color:#555555;">Rationale</h4>
<ul>
    <li style="color:#555555;"><b>User Experience:</b> Removing a fragment increases navigability and addional icons and progress bar help enhance user expereince and frienly for people with disabilities.</li>
    <li style="color:#555555;"><b>Ease of Implementation:</b> Reused code from view plan fragment, retrieve icons from the internet, and syncing progress bar with current exhibit which require relatively low implementation efforts.</li>
</ul>

Chosen option: **Green-themed design with specific layout and UI elements**.

#### Reasons for Choice:

- **Green Theme**: We chose light and dark green colors for the app frame and theme to reflect the natural environment of a zoo. Green is associated with nature, which aligns well with our app's purpose.
  
- **Exhibit Detail Page**: We centered the image of the animal on the top of the page to provide a clear view of the animal's lifestyle and appearance. This helps users get a good sense of what the animal looks like. Below the image, we included the conservation status and availability using clean icons to intuitively inform users whether they can visit the exhibit. A detailed description is provided further down the page for users who want more information about the animal.

- **Planning Page**: For the planning page, we used circular images that look like icons of the animals to format the animal list. This design choice ensures a clean UI for such lists while providing good intuition about the animals included in the lists.

- **Search Page**: On the search page, we added images and some recommended animals for search. This feature allows users to search for an animal even if they do not know its name, enhancing the usability of the app.

<h3 style="color:#555555;">Implications</h3>
<p style="border-top: 2px solid lightgray;"></p>

<h4 style="color:#555555;">Rationale</h4>
<ul>
    <li style="color:#555555;"><b>Development:</b> Require sometimes to link progress bar with current user's status on the plan and getting feature icons from the internet.</li>
    <li style="color:#555555;"><b>Testing:</b> Extensive testing across different user's plan sizes and browsers to ensure consistency.</li>
</ul>

<h3 style="color:#555555;">Status</h3>
<p style="border-top: 2px solid lightgray;"></p>

<h5 style="color:#555555;">Merged</h5>

<h3 style="color:#555555;">Consquences</h3>
<p style="border-top: 2px solid lightgray;"></p>

<p style="color:#555555;">The choices provide a better user experience with less clickings and appealing ui features, aligning with the zoo app's goal of enhancing visitor satisfaction and operational efficiency</p>
