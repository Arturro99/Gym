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
  </ol>
</details>

## About the project
Gym is a multi-access system providing functionalities like bookings places for group activities in a local gym, managing customers' diets, training plans and
in general managing customer's accounts.
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

### Frontend
