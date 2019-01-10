# restful-notes - RESTful API WebService
WebService responsible for managing and storing in database simple notes

## Required tools
1. Apache Tomcat - http://tomcat.apache.org/ - **Server**
2. H2Database - http://www.h2database.com - **Database**
3. Maven - https://maven.apache.org/ - **Build Tool**
4. Java JDK 8 - https://www.oracle.com/technetwork/java/javase/downloads/index.html

### Database setup
1. Database in file is created when WebService is starting - **H2Database Server Mode**
2. Database in memory is created when JUnit tests are running - **H2Database InMemory Mode**

### Build and run project
1. Build using Maven - **mvn clean install**
2. Run Apache Tomcat - **TOMCAT_HOME/bin/startup.sh**
3. Deploy WebService using Maven Plugin - mvn tomcat7:deploy or mvn tomcat7:redeploy
4. Test WebService using Postman - **example requests in [Example Usage](#example-usage)**
5. Test WebService using JUnit tests - **included in project source code**
6. Check H2Database in file (Server Mode) - **when H2Database is running, click in H2Console "Start Browser"**

### Example usage
Postman requests - **included in project source code** - [File](restful-notes.postman_collection.json)

## WebService Endpoints list
1. POST /restful-notes/notes/ - **create new note**
2. GET /restful-notes/notes/ - **get all notes**
3. GET /restful-notes/notes/{id} - **get single note details**
4. PUT /restful-notes/notes/{id} - **update single note details**
5. DEL /restful-notes/notes/{id} - **delete single note**
6. GET /restful-notes/notes/history/{id} - **get single note history**
