# Gym Project

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#used-technologies">Used technologies</a>
      <ul>
        <li><a href="#backend">Backend</a></li>
        <li><a href="#frontend">Frontend</a></li>
      </ul>
    </li>
    <li>
      <a href="#technical-details">Technical details</a>
      <ul>
        <li><a href="#backend">Backend</a></li>
        <li><a href="#frontend">Frontend</a></li>
      </ul>
    </li>
    <li>
      <a href="#sample-views">Sample views</a>
    </li>
  </ol>
</details>

## About the project
Gym is a multi-access system providing functionalities like bookings places for group activities in a local gym, managing customers' diets, training plans and
in general managing customers' accounts.
The main functionality - booking places is mainly handled by a kind of preferential algorithm which is responsible for i.a.
deciding about the order of booking in case of considerable interest in particular activities. 

The architecture of the system is also worth pointing out. It is driven by hexagonal architecture which facilitated easier isolated testing and allowed the possibility
to smoothly replace or add adapters and ports in case of any further changes. 
## Used technologies

### Backend

* JDK 11
* Spring Boot, Spring Web, Spring Data, Spring Security
* Hibernate
* PostgreSQL
* JUnit 5, Mockito 

### Frontend

* Javascript
* React
* Bootstrap 5

## Technical details

### Backend
Communication between server and client is handled via REST. The driving/primary adapters, which are entry points to the hexagon, are responsible for implementing endpoints whereas the driven/secondary ones do the ORM.
The system supports transactions along with obeying numerous constraints that were justified by business reasons.
Exceptions are handled via advice mechanism and then a proper error code with particular error key and timestamp are sent to the client.
Attention was also paid to the security issues which were basically handled by Spring Security. JWT mechanism is used for authentication. In order to maintain
its credibility it was signed with HS256 algorithm. It also support refreshing after each succeeded call. Authorization is handled by SecurityContext which is injected whenever needed and a proper HttpSecurity configuration. HTTPS protocol is used to maintain data confidentiality.
Cors configuration was also included aiming to facilitate communication between backend and frontend.
Logging was implemented using aspect programming and a Slf4j library.
Apart from the info provided above, the project was created with the help of some additional libraries:
* **Liquibase** useful for tracking database changes and facilitating its creation, initiation and configuration.
* **OpenAPI 3.0** helpful in auto-generating REST interfaces, DTOs and swagger docs.
* **Mapstruct** doing the donkey work in generating mappers for both driven and driving object models.
* 
### Frontend
The view was implemented in the form of Single Page Application. It supports internationalization depending on browser language settings. 
It also contains all required validation on the user side with proper error indication.
Frontend handles received JWT, decodes it and then saves particular user information like roles in the local storage in order to use it to dynamically display page data.
While implementing the view of the application some additional Javascript libraries were used:
* **axios** providing interceptors and http methods.
* **Joi** providing validation tools.
* **i18next** enabling internationalization configuration.

## Sample views

