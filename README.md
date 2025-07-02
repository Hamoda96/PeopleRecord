# peopleRecord

peopleRecord is an Android application designed to manage personal information. It features two main screens implemented both in Jetpack Compose and XML layouts.

## Features

1. **Launcher Screen**:
   - **Buttons**:
     - **Open Compose**: Launches the Compose-based implementation
     - **Open Views**: Launches the XML-based implementation

2. **user Screen**:
   - **Functionality**: Allows users to enter personal information including name, job title, age, and gender. This data is saved to a Room database when the "Save" button is clicked.
   - **Validation**: Includes form validation to ensure that inputs are valid before saving.
   - **Components**:
     - **Gender**:
       - Jetpack Compose: Implemented using `DropDownMenu`.
       - XML: Implemented using `Spinner`.
3. **users screen**:
   - **Functionality**: Displays a list of all users stored in the database. Users can delete individual entries from this list.


## Technologies Used

- **Room Database**: For local data storage and retrieval.
- **Koin**: For dependency injection.
- **MVVM**: 
  - **MVVM (Model-View-ViewModel)**: Architecture for managing UI-related data in a lifecycle-conscious way.
- **Jetpack Compose**: For building native UIs with a declarative approach.
- **Coroutines**: For handling asynchronous operations.
- **Material3**: Provides modern UI components and design system.
- **Navigation Components**: For handling navigation within the app, including transitions between screens.

## Architecture

- **MVVM**: Separates UI (View) from business logic (Model) through a ViewModel, managing the interaction between them.

## Screens

### user Screen

- **Purpose**: Collect and save user data.
- **Components**:
  - Input fields: Name, Job Title, Age, Gender.
  - Save button for storing data in the Room database.
  - Validation messages for input errors.
  - Implemented using Jetpack Compose and XML.
  - **Gender**:
    - Compose: `DropDownMenu` for selecting job title and gender.
    - XML: `Spinner` for selecting job title and gender.

### Screenshots

#### Compose Implementation
<img src="https://i.postimg.cc/brVhwZDd/compose1.jpg" width="400" alt="Compose user Screen">

#### XML Implementation
<img src="https://i.postimg.cc/8c62NZSX/xml1.jpg" width="400" alt="XML user Screen">

### users

- **Purpose**: Display and manage a list of all users stored in the database.
- **Components**:
  - List of users with a delete option for each entry.
  - Implemented using Jetpack Compose and XML.

### Screenshots

#### Compose Implementation
<img src="https://i.postimg.cc/XNs38zH3/compose2.jpg" width="400" alt="Compose users List">

#### XML Implementation
<img src="https://i.postimg.cc/ZYvt85dP/xml2.jpg" width="400" alt="XML users List">
