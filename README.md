## BlocAdmin
Web application with a micro-services architecture that allows the administrator of any building complex to manage the information regarding the buildings, residents, resident requests, budgets and expenses, as well as keeping all the information in one place.

## Getting Started
Import the project into an IDE of your choosing, supporting Java applications, Maven and Spring. Ensure you have the ports for each micro-service (see application.properties) or change them to ones you know are available.
The application communicates across services through REST and it's split up in four micro-services: 
* budget-service, that can later on be expanded to handle more complex operations for budget management;
* operation-service, where most of the back-end work is done, to handle expenses, requests and households;
* user-service, handling authentication (basic, database authentication for this project, to later be enhanced using authentication providers in the cloud version of this application), as well as the information on different user types;
* web-service - front-end service, mapping the REST requests.

The application is intended to showcase a migration from a regular monolithic web based application to a micro-services one. See my repository BlocAdmin to check out the monolithic version.

## Built With
* Spring Boot - an open source Java-based framework used to create micro services for web application.
* H2 - open-source lightweight Java database.
* Bootstrap - free and open-source CSS framework directed at responsive, mobile-first front-end web development.
* Thymeleaf - Java XML/XHTML/HTML5 template engine workable with web and non-web applications.
* Log4j - logging application information.
* Librepdf - free Java library for creating and editing PDF files, used to export PDF files.

## Author
* **Geanina Chiricuta**


