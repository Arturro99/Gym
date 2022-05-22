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
        <ul>
          <li><a href="#login-form">Login form</a></li>
          <li><a href="#register-form">Register form</a></li>
          <li><a href="#bookings-list">Bookings list</a></li>
          <li><a href="#activities-list">Activities list</a></li>
          <li><a href="#user-details">User details</a></li>
      </ul>
    </li>
  </ol>
</details>

## About the project
Gym is a multi-access system providing functionalities like bookings places for group activities in a local gym, managing customers' diets, training plans and
in general managing customers' accounts.
The main functionality - booking places is mainly handled by a kind of preferential algorithm which is responsible for i.a.
deciding about the order of booking in case of considerable interest in particular activities. 

The architecture of the system is also worth pointing out. It is driven by hexagonal architecture which facilitates isolated testing and allowes the possibility
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
Logging was implemented using aspect programming and a Slf4j library.

Attention was also paid to the security issues which are basically handled by Spring Security. JWT mechanism is used for authentication. In order to maintain
its credibility it was signed with HS256 algorithm. It also supports refreshing after each succeeded call. Authorization is handled by SecurityContext which is injected whenever needed and a proper HttpSecurity configuration. HTTPS protocol is used to maintain data confidentiality.
Cors configuration was also included aiming to facilitate communication between backend and frontend.

Apart from the info provided above, the project was created with the help of some additional libraries:
* **Liquibase** useful for tracking database changes and facilitating its creation, initiation and configuration.
* **OpenAPI 3.0** helpful in auto-generating REST interfaces, DTOs and swagger docs.
* **Mapstruct** doing the donkey work in generating mappers for both driven and driving object models.

### Frontend
The view was implemented in the form of Single Page Application. It supports internationalization depending on browser language settings. 
It also contains all required validation on the user side with proper error indication.
Frontend handles received JWT, decodes it and then saves particular user information like roles in the local storage in order to use it to dynamically display page data.

While implementing the view of the application some additional Javascript libraries were used:
* **axios** providing interceptors and http methods.
* **Joi** providing validation tools.
* **i18next** enabling internationalization configuration.

## Sample views

### Login form
![image](https://user-images.githubusercontent.com/48444511/169711840-71657abe-f672-4c1a-848f-7ff5f0c8c7a6.png)

### Register form
![image](https://user-images.githubusercontent.com/48444511/169712361-aa52a3f7-173c-4145-bf56-a5765eed372d.png)

### Bookings list
![image](https://user-images.githubusercontent.com/48444511/169712104-1ddaf3ab-0dee-4c6e-8e61-9882e0f19968.png)

### Activities list
![image](https://user-images.githubusercontent.com/48444511/169712147-5e9ac531-1f67-448d-b0fa-46792d028a5e.png)

### User details
![image](https://user-images.githubusercontent.com/48444511/169712445-74f27d01-d335-434b-a4f5-1c101e37420d.png)

