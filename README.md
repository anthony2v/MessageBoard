# SOEN 387 Assignment 3

This assignment is the second iteration on the MessageBoard System that was implemented in the previous assignment. It consists of the following sections: 
1) HandlingUsersandGroups 
2) TestDrivenDevelopment
3) UsingPatterns
4) UsingWebPresentationPatterns

See `documentation/SOEN387_Assignment_3.pdf` for more details.

<br/>

## Table of Contents
- [SOEN 387 Assignment 3](#soen-387-assignment-3)
  - [Table of Contents](#table-of-contents)
  - [Authors](#authors)
  - [Getting Started](#getting-started)
    - [Create local MySql database](#create-local-mysql-database)
    - [Create local database configuration file](#create-local-database-configuration-file)
    - [Integrated Development Environment (IDE) Setup](#integrated-development-environment-ide-setup)
  - [Grading Scheme](#grading-scheme)

<br/>

## Authors
- **Daniel Rinaldi - 40010464 (Team Leader)**
- Anthony Van Voorst - 40001890
- Tiffany Ah King - 40082976
- Khadija Subtain - 40040952

<br/>

## Getting Started
### Create local MySql database
All of the DDL required to create the database structure and fill it with any initial data is in 2 scripts located at `database/db_structure.sql` and `database/db_initial_data.sql` for the structure and initial data respectively. 
Simply run these sql scripts (first the structure, then the data) and you should have the correct database setup.

### Create local database configuration file
Make a copy of the file located at `src/main/webapp/WEB-INF/hibernate.cfg.example.properties` and name the copy `hibernate.cfg.properties`. 
This file will contain the local db credentials and you have to fill it in.

### Integrated Development Environment (IDE) Setup
We used Eclipse (STS) as our IDE. This project is a basic dynamic web project that uses maven configuration. 
1. After cloning this repository, in order to setup the project in Eclipse, switch to Java EE perspective and right click in the project explorer view and select `import > import...`. In the popup dialog, search for and select `Existing Projects into Workspace`. In the next dialog window select the project folder and press finish. The project should show up as a dynamic web project in the project explorer view.
2. Right click <u>on the project</u> in the project explorer view and click `Configure > Convert to Maven Project`. In the dialog that pops up select the project and hit finish. Now the project should have maven configuration setup.
3. Add the server run configuration. Go to `Run > Run Configuratrions` and create a new tomcat server configuration.
4. For some reason, when creating the server configuration, there is a dash (illegal) added as the defualt admin port for tomcat. Open the Servers view by going to `Window > Show View > Servers`. Double click on the tomcat sever to be able to modify it. In the Overview tab, under the `Ports` accordian menu, change `Tomcat admin port` to 0 or some other number other than a `-`. Save the file (`ctrl+s`) and close it.
5. You need to make sure that you have added tomcat server library to the classpath. Right click <u>on the project</u> in the project explorer view and click on `properties`. In the project properties window, select `Java Build Path`, click on the `Libraries` tab and make sure that Aapache Tomcat v9.0 is listed under Classpath. If not you will need to add it. Select "Classpath" then click on the `Add Library` button on the right and select `Server Runtime > Aapache Tomcat v9.0`.
6. We use `/` as the context root for this web project. So you need to right click <u>on the project</u> in the project explorer view and click on `properties`. In the project properties window, select `Web Project Settings` in the left hand menu. Change the context root property to `/`.
7. Add the web project to the server. Open the Servers view by going to `Window > Show View > Servers`. Right click on the tomcat sever and select `Add and Remove...`. Add the web project to the configured list of projects and press finish.
8. Run the run configuration that you had setup in step 3. If everything was done correctly, you should gett a message in the console saying something like : `INFO: Server startup in [5982] milliseconds`. Next open a web browser and navigate to `http://localhost:{port_number}/`.

<br/>

## Grading Scheme
| Grading Criteria            | Points    |
| :-------------------------- | --------: |
| System Functionality        | 20        |
| The Business Layer          | 15        |
| The Download Servlet        | 15        |
| Encoding & Error Handling   | 10        |
| Front-End                   | 25        |
| Source Control              | 5         |
| Documentation               | 10        |
| DAL [and BLOBs (bonus)]     | 10        |
| Total                       | 100 (+10) |

<br/>
