# interval merger
tool for merging intervals

### assumptions
- interval values are integers
- edges are included (means \[2,3\] \[3,9\] will be merged to \[2,9\])

### prerequisite
- Java 11 JRE must be installed
- JAVA_HOME environment variable must be set to the java 11 directory


### run the application
- clone or download end extract the repository
- got into main directory and execute ./mvnw clean package (.\mvnw for windows command line like cmd or powershell)
- execute: java -jar target/intervalmerge-0.0.1-SNAPSHOT.jar
- open in a browser http://localhost:8080

### input syntax
- example: \[1,2\] \[3,4\] ...
- spaces between intervals
- comma between start and end inside an interval
