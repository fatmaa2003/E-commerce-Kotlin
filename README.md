# Akhein-App
AKHEINApp allows users to write and save diary entries with the option to include photos and expressive emojis to capture the essence of each moment. 


<p align="center">
<img img width="200" height="400" src="./readme-assets/screenshots/image1.jpeg"> &nbsp;&nbsp;&nbsp;
<img img width="200" height="400" src="./readme-assets/screenshots/image2.jpeg"> &nbsp;&nbsp;&nbsp;   
<img img width="200" height="400" src="./readme-assets/screenshots/image3.jpeg"> &nbsp;&nbsp;&nbsp; 


</p>

Here's an overview of the app's features:
- **Market Feature**: The Market section is divided into four main categories: Featured, Collection, Stores, and Tags.

- **Profile Feature**: The Profile section contains essential user information and settings. It displays the user's profile name, username, and offers access to manage preferences, cards, and addresses. Users can update their personal details and control various aspects of their account

- **Data Module**:The Data module in the Android Diary App is responsible for managing data storage and retrieval using both MongoDB and Room. It handles the setup and integration of Mongo Realm, allowing seamless connectivity to the MongoDB backend. The Data module provides functionalities for inserting, fetching, updating, and deleting diary entries in the MongoDB database. Additionally, DiaryApp leverages Room Librayto provide offline access and local caching of diary entries, enhancing the app's responsiveness and offline capabilities.

- **Common/Core Modules**: The app includes two core modules: *UI* and *Utils*. The UI module contains common Compose functions, components, and UI-related code that are shared across different features. This module promotes code reuse and consistency in the app's user interface. The Utils module provides essential utilities such as model classes, connectivity observers, constants, strings and drawable resources. It ensures a centralized and efficient management of commonly used resources and functionalities.

- **Feature Modules**: This module includes three destinations: Auth, Home and Write. 'Auth' handles the authentication with the users. 'Home' displays all the data/diaries in our application. And 'Write' module allows you to create a new diary note in your app.

### Navigation
The app has :three: screen destinations which use Compose Navigation to manage navigation.

| :feature:Feed&Profile               | :flow:Purhcase Flow                 | :feature:Search&Cart                |
|-------------------------------------|-------------------------------------|-------------------------------------|
| ![](./readme-assets/gifs/feedandprofile.gif) | ![](./readme-assets/gifs/purchaseflow.gif) | ![](./readme-assets/gifs/searchandcart.gif) |

- **Authentication Feature**: This feature focuses on user authentication and validation. It utilizes Google Sign-In to ensure that users can securely access their diary entries. By authenticating users, the app guarantees that only authorized individuals can interact with their personal diaries.

- **Home Feature**: The Home feature is responsible for displaying and filtering diary entries based on the date. It provides a user-friendly interface to navigate through diary entries and quickly filter diaries by specific dates. Additional selections can be accessed through the Navigation Drawer.

- **Write Feature**: The Write feature enables users to create new diary entries or modify existing ones. It offers a seamless and intuitive interface for users to capture and document their thoughts, moments, and memories. DiaryApp empowers the users to personalize content by adding emojis and accompanying images

# :memo: Contributor :memo:
- [@fatmaa2003](https://github.com/fatmaa2003)
- [@nadashahe](https://github.com/nadashahe)
- [@sheehata](https://github.com/sheehata)
- [@hanann03](https://github.com/hanann03)
- [@mostafat754](https://github.com/mostafat754)