# RESTful API for Contact Management

## Description

user-friendly restful api for contact management with user management, address management and login with token

## Layers

### Presentation Layer

- I use **Object Mapper** to translate JSON to object
- I use **Validation** to validate the request
- I use **Argument Resolver** to automate filling object argument

### Business Layer

- I use **Service** to save all my business logic like register, login, get, update, logout, create
- I apply **Authorization and Validation** to get, update or remove the data be in the form of **unique token and model** of request
- I use **Spring Security** feature like BCrypt to hash password

### Data Access Layer

- I use **Repository** to interact with database like insert, update, delete data in database
- In Repository i create a number of custom query method

### Infrastructure Layer

- I config database in application.properties file
- I activate hibernate sql mode to show query when do unit test

## Expectation

I still working at this project, my plan is **adding front end like reactJS**