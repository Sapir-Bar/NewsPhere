# Webservice 

This Node.js project is designed to function as a RESTful web service, facilitating communication between the client and a local MongoDB server using Mongoose. Its primary aim is to handle client requests and manage interactions with the MongoDB database.

__<span style="color:red"> The application uses the custom-env module to load environment variables from configuration files located in a directory named config. However, despite defining the relative path correctly, the application is searching for the config directory one level above its expected location. To resolve this issue, it's necessary to copy the config directory one level up relative to the app.js file. Thank you for your consideration. </span>__

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Cloning the Repository](#cloning-the-repository)
3. [Init MongoDB](#init-mongodb)
4. [Running the Application](#running-the-application)
5. [Jira](#jira-integration)

## Prerequisites
Before starting with the web service, ensure that your development environment meets the following requirements:

- [MongoDB Compass](https://www.mongodb.com/try/download/shell) installed on your system.
- [Git](https://git-scm.com/) installed for cloning the project.

## Cloning the Repository
You can clone the repository either through Visual Studio Code or via Git Bash:

### Using Visual Studio Code:
1. Open Visual Studio Code.
2. Choose "Get from Version Control" and provide the repository URL: https://github.com/Tamarmic/part3.git

### Using Git Bash:
```bash
git clone https://github.com/Tamarmic/part3.git
```

### Init MongoDB
1. Create the appropriate database.
2. Populate the database with the required collections: posts and users. You can use the provided .json files attached to this project, named 'foobar.posts.base64.json' and 'foobar.users.base64.json'.

## Running the Application
Start the MongoDB server by running 'connect'.
Launch the web service by opening a Node.js terminal and executing the command 'npm start'.
Currently, the web service runs on port 12345 (you can modify it in the config directory), while the MongoDB server operates on port 27017.

## Jira Integration
Access our Jira project for task management: [Jira](https://tamar124.atlassian.net/jira/software/projects/KAN/boards/1)
