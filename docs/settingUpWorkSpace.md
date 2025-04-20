# About the project - how to combine everything and run

The project consists of 4 repositories:
1. The Bloom server filters the user in order to protect the users of the application by preventing the possibility of adding malicious links to the application or the website.
2. The data server - connects to the Mongo DB database and imports the data from there. The server is connected to the Mongo DB database and provides the information from there to the site and the application while checking for the presence of malicious links using the Bloom Filter server.
3. The app - allows you to access Facebook through an app written for Android, communicates with the data server.
4. The website - to enable access to Facebook through the browser. Written with React, communicates with the data server.
This Node.js project is designed to function as a RESTful web service, facilitating communication between the client and a local MongoDB server using Mongoose. Its primary aim is to handle client requests and manage interactions with the MongoDB database.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Cloning the repositories](#cloning-the-repositories)
3. [Init MongoDB](#init-mongodb)
4. [Running the Application](#running-the-application)
5. [Jira](#jira-integration)

## Prerequisites
Before starting with the web service, ensure that your development environment meets the following requirements:

- [MongoDB Compass](https://www.mongodb.com/try/download/shell) installed on your system.
- [Git](https://git-scm.com/) installed for cloning the project.

## Cloning the repositories
You can clone the repository either through Visual Studio Code or via Git Bash:

### Using Visual Studio Code:
1. Open Visual Studio Code.
2. Choose "Get from Version Control" and provide the repository URL:
*  https://github.com/Tamarmic/part3.git
* https://github.com/Tamarmic/FooBar.git 
* https://github.com/Tamarmic/Foobar2.git
* https://github.com/Sapir-Bar/Android-Application.git 

__<span style="color:red"> For each repository make sure to pull from branch part 4 </span>__

### Using Git Bash:
```bash
git clone https://github.com/Tamarmic/part3.git
```
```bash
git clone https://github.com/Tamarmic/FooBar.git
```
``` bash
git clone https://github.com/Tamarmic/Foobar2.git
```
```bash
git clone https://github.com/Sapir-Bar/Android-Application.git 
```

### Init MongoDB
1. Create the appropriate database.
2. Populate the database with the required collections: posts and users. You can use the provided .json files attached to this project, named 'foobar.posts.base64.json' and 'foobar.users.base64.json'.

## Running the Application

Start the MongoDB server by running 'connect'.
Launch the web service by opening a Node.js terminal and executing the command 'npm start'.
Currently, the web service runs on port 12345 (you can modify it in the config directory), while the MongoDB server operates on port 27017.


1. Start by running the Bloom Filter server.
Go to the FooBar repository, open the wsl terminal
and run the following commands:
cd src
g++ ./HashFunction.cpp ./bloomFilterArray.cpp ./bloomFilter.cpp ./bloomFilterInit.cpp ./commandInit.cpp ./ICommand.cpp ./InputValidator.cpp ./runner.cpp ./UrlAddCommand.cpp ./UrlCheckCommand . cpp ./Data.cpp ./Main.cpp -pthread
./a.out
2. Go to the part3 repositories and start running the data server by opening the terminal and running: 'npm start'
3. Go to the website or app of your choice:
For the site-
Go to the Foobar2 repository and in the terminal run 'npm start'
The browser will open automatically.
For the app-
Please take a look at the "android_application" wiki file for detailed instructions. 

## Jira Integration
Access our Jira project for task management: [Jira](https://tamar124.atlassian.net/jira/software/projects/KAN/boards/1)

## Now everything should work fine. You can connect to the application and enjoy additional features that you can learn about in the files: settingUpWorkSpace.md, react.md and android_application.md.