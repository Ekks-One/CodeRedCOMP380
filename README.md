# CodeRedCOMP380
Welcome to Code Red Ecommerce

❗🔴Code Red E-Commerce Platform🔴❗
A multi-step, transactional e-commerce solution built by a group using Java (OOP), JavaFX, and MySQL. The project was engineered for data integrity and user consistency.

🎥 Demonstration Video
Watch a short video demonstrating the Universal Cart System and full application flow.
www.youtube.com/watch?v=i_61l_JlSxs&feature=youtu.be


🟦Demo Description

This video showcases the core functionality and system architecture. The demonstration focuses on the Universal Cart System, which Xavier designed and implemented using the Singleton design pattern to ensure only one cart instance exists, preventing state inconsistency and guaranteeing a trustworthy checkout flow.

The demo also runs through the full operational procedure, including product searching, dynamic item viewing (with color/size options), and order finalization with automated email confirmation.

Our product list is dynamic variant handled (where the availability of one option, like material, is conditional on the selection of another, like size or color), and order finalization with automated email confirmation.

This showcases our team's ability to model complex, real-world retail constraints within a relational database and presetn the data dynamically in the user interface. 

🚀 How to Run the Program (Official Setup)
This application is a Java project that requires the JavaFX SDK and a connected SQL Server instance to run.

Setup Instructions
1. Clone or Download the Project
  a. Clone the project repository from version control (Git) or from the command prompt:
    **git clone https://github.com/Ekks-One/CodeRedCOMP380.git**

2. Configure Database
  a. Open SQL server manager studio
  b. create the SwagShop database
  c. import database schema and data

3. Configure Application
  a. Locate config.json and update connection details

4. Setup JavaFX
  a. Download the JavaFX SDK from Gluon
  b. Add the JavaFX libraries to the classpath
  c. Configure VM options for JavaFX

5. Open project in IDE(VsCode)
  a. Configure IDE to use the correct JDK and JavaFX libraries
  b. Run the App.java class as a JavaFX application

Troubleshooting:
- Database Connection Error : Ensure SQL Server is running and accessible
- JavaFX Runtime Error : Ensure libraries are correctly added to classpath
- Missing Dependencies: Ensure all require dependencies are included in the project
