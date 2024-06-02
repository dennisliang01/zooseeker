5-15-2024
US2-2 Database Selection

Progress:

Our user story subteam collectively decided to proceed in the development of the application using the native Android Studio singleton database pattern as opposed to leveraging a third-party hosting service. In our deliberations, plans were made to create at least 5 unique daos to represent five different data types to be stored within the singleton. The schemas for object types which were created in the original iteration of the ZooSeeker application were left relatively unchanged, with the exception of the animal exhibit schema, which now contains a new field describing whether or not the given exhibit is available for visitors. Supplementing these existing dao types, two new types introduced to facilitate new features include the general location data type, used to represent common areas such as restaurants, restrooms, and shows, and also the location availability data type, which is used to house a boolean representing whether or not a given location is open or closed. Recurring data types left unmodified since their original implementation include daos for storing the current user’s navigation plan, the graph dao and its semantic representation of the zoo layout, the node item dao for key stopping points within the zoo, and the edge item dao for pathways between nodes.

Justification:

We decided to continue using the Android Studio local database due to the needs of our app and features. With the expanded feature set for our app, we need to be able to store information such as zoo exhibit status, animal information, and connecting paths between exhibits. We decided to not go with a remote database because we don’t really have a need for it and it will add unnecessary complexity to our application and development process. We decided to have at least 5 different tables for the database because of the different structure of the data such as zoo path and zoo exhibit times.

