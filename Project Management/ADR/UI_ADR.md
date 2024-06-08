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

<h3 style="color:#555555;">Decision Outcomes</h3>
<p style="border-top: 2px solid lightgray;"></p>

<h4 style="color:#555555;">Rationale</h4>
<ul>
    <li style="color:#555555;"><b>User Experience:</b> Removing a fragment increases navigability and addional icons and progress bar help enhance user expereince and frienly for people with disabilities.</li>
    <li style="color:#555555;"><b>Ease of Implementation:</b> Reused code from view plan fragment, retrieve icons from the internet, and syncing progress bar with current exhibit which require relatively low implementation efforts.</li>
</ul>

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
