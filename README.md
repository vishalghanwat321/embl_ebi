# EMBL_EBI
Embl_ebi back-end java application assignment

Backend application that handles a person details. Below are the core tech-stack used to implement this application:-

Java programming language
JDK1.8
Spring-boot, Spring framework
h2 in memory database
Gradle 4.4
IDE STS tool 
Swagger
Junit/Mockito

The project is implemented using IDE STS & used basic in memory auth to handle API security.

Use below credentials while accessing services.
User:- embl_ebi
Password:- password


I have used the h2 database(http://localhost:8082/h2-console) that runs in memory to store the data.
Post importing application into workspace you have to use below gradle command to start the application
"clean build test assemble bootRun"

Above command will also create the EMBL_EBI.jar executable jar file, you can execute only that also using command "java -jar <path_to_jar>/EMBL_EBI.jar" 

It will take some time to start the application post that you can access the services as below. 

Swagger URL http://<your_machine_ip>:8082/swagger-ui.html or http://localhost:8082/swagger-ui.html  to test the services.