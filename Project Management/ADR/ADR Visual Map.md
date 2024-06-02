### Visual Map Implementation

Our team has decided that a visual map is important for us to visualize our zoo and for zoo visitors to be able to see where they are and help follow the directions given on the direction screen. With the given longitude and latitude coordinates in the initial JSON file, we were able to map out figure 1. As can probably be seen, this is not the greatest design. The previous team member stated that they received the data and weren’t able to check it for accuracy. The number labels on the graph represent feet between the nodes, and as can be seen it is very skewed. Since we were unhappy with a lot of the connection points we decided to attempt a new design that we could better scale to size and make our exhibits and nodes. The previous street names and exhibits will be preserved, but new ones will be added. 

![Figure 1](../Reference%20Assets/OriginalMapLayout.jpg)

We wanted to base our zoo in reality as much as possible. Since our goal it to be as close to a "real" zoo experience as possible. The inspirations we followed were from low art color blocked version like the following:

![Figure 2](../Reference%20Assets/LaZooMap.jpeg)

These do a great job of telling you all the information that you need to know but they don't look the greatest. We wanted some influences from versions with a bit more artistic style:

![Figure 3](../Reference%20Assets/PittsburgZooMap.jpeg)

This was a little to in depth for our purposes but it provided excellent room for a middle ground. The Pittsburgh zoo had the same features as the color block map just with more artistic design.

We started by creating a generic shape with paths and some structures to it. The obvious question then was how do we decide how large exhibits should be? What animals go where? It turns out there are [entire forums dedicated to gauging enclosure sizes for different zoos throughout the world](https://www.zoochat.com/community/threads/zoo-enclosure-sizes-revised.465984/), so we took the average of exhibit sizes for animals that we wanted to see in our zoos. Using a grid layout in the art tool we created in and calling each grid 60x60ft we were able to accurately gauge exhibit sizes. We based the animal groups based off the LA zoos blocking off sections for animals:

![Figure 4](../Reference%20Assets/InProgressZoo.jpeg)

This is an in progress image of the map, we started to figure out what exactly could fit in the arrangements that we made and realized that we had to make adjustments. We also allocated space for restaurants / food kiosks, restrooms, and the ampitheater space. Eventually we got here:

![Figure 5](../Reference%20Assets/FinalZooMap.png)

We're very happy with how it turned out, and based on how we needed to weave things together we then had data for every exhibit, it's size, and where things were in relation to each other. We then used this map in the map portion of zooseeker, but also to find every edge and node for the new JSON files that would need to accompany the map. We are very proud to present Powell Zoo.