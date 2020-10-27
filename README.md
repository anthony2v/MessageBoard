# SOEN 387 Assignment 2

In this assignment we implement a small web application that simulates a simple Message Board System.

See `documentation/SOEN387_Assignment2.pdf` for more details.

<br/>

## Table of Contents
- [SOEN 387 Assignment 2](#soen-387-assignment-2)
  - [Table of Contents](#table-of-contents)
  - [Authors](#authors)
  - [Getting Started](#getting-started)
    - [Create local database configuration file](#create-local-database-configuration-file)
    - [Create local MySql database](#create-local-mysql-database)
  - [Grading Scheme](#grading-scheme)

<br/>

## Authors
- **Daniel Rinaldi - 40010464 (Team Leader)**
- Anthony Van Voorst - 40001890
- Tiffany Ah King - 40082976
- Khadija Subtain - 40040952

<br/>

## Getting Started
### Create local database configuration file
Make a copy of the file located at `src/main/webapp/WEB-INF/hibernate.cfg.example.properties` and name the copy `hibernate.cfg.properties`. This file will contain the local db credentials.

### Create local MySql database
All of the DDL required to create the database structure and fill it with any initial data is located at `documentation/db_structure_and_initial_data.sql`. Simply run this sql script and you should have the correct database setup.

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
| DAL and BLOBs               | 10        |
| Total                       | 100 (+10) |

<br/>
