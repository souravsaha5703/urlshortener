# URL Shortener

A backend URL shortening service built with **Spring Boot, Spring Data JPA, Hibernate, and MySQL**. The application generates unique short URLs, redirects users to the original URL, tracks click activity, and provides basic analytics for each shortened URL.

## Features

- Create shortened URLs from original URLs
- Generate unique 6-character short codes
- Redirect short URLs to their original URLs
- Track total click count
- Record individual click events
- Capture:
  - Click timestamp
  - User-Agent
  - Referrer
- View URL analytics
- Request validation for URLs
- Global exception handling
- RESTful API architecture
- MySQL database persistence using JPA/Hibernate

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

### Validation & API
- Jakarta Bean Validation
- REST APIs
- DTO-based responses

### Deployment
- Docker
- Render

## Project Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Main Components

```text
src/main/java/com/example/urlshortner/
│
├── controller/
│   ├── UrlController.java
│   └── RedirectController.java
│
├── service/
│   └── UrlService.java
│
├── repository/
│   ├── UrlRepository.java
│   └── ClickEventRepository.java
│
├── entity/
│   ├── Url.java
│   └── ClickEvent.java
│
├── dto/
│   ├── UrlRequest.java
│   ├── ClickEventResponse.java
│   └── AnalyticsResponse.java
│
└── exception/
    ├── UrlNotFoundException.java
    └── GlobalExceptionHandler.java
```

## Database Design

The application uses two main entities.

### URL

Stores information about each shortened URL.

| Field | Description |
|---|---|
| `id` | Unique URL identifier |
| `originalUrl` | Original destination URL |
| `shortCode` | Unique generated short code |
| `createdAt` | URL creation timestamp |
| `clickCount` | Total number of clicks |

### ClickEvent

Stores information about individual clicks.

| Field | Description |
|---|---|
| `id` | Unique click event identifier |
| `url_id` | Reference to the shortened URL |
| `clickedAt` | Time of the click |
| `userAgent` | Browser/client information |
| `referrer` | Referring URL |

Relationship:

```text
Url
 │
 │ 1
 │
 │
 │ *
 ▼
ClickEvent
```

A single shortened URL can have multiple click events.

## API Endpoints

### 1. Create Short URL

**POST**

```text
/api/urls
```

Request:

```json
{
  "originalUrl": "https://www.example.com"
}
```

Example response:

```json
{
  "id": 1,
  "originalUrl": "https://www.example.com",
  "shortCode": "a8F3kL",
  "createdAt": "2026-10-03T20:00:00",
  "clickCount": 0
}
```

### 2. Redirect to Original URL

**GET**

```text
/{shortCode}
```

Example:

```text
/a8F3kL
```

The application:

1. Finds the URL using the short code.
2. Increments the click count.
3. Records a click event.
4. Redirects the user to the original URL.

### 3. Get URL Analytics

**GET**

```text
/api/urls/{shortCode}/analytics
```

Example:

```text
/api/urls/a8F3kL/analytics
```

Example response:

```json
{
  "shortCode": "a8F3kL",
  "originalUrl": "https://www.example.com",
  "createdAt": "2026-10-03T20:00:00",
  "totalClicks": 3,
  "clicks": [
    {
      "clickedAt": "2026-10-03T20:10:00",
      "userAgent": "Mozilla/5.0",
      "referrer": "https://google.com"
    }
  ]
}
```

## Short Code Generation

The application generates a random 6-character short code using UUID-based generation.

Before saving the code, the application checks the database to ensure that the generated code does not already exist.

```text
Generate short code
       ↓
Check database
       ↓
Already exists?
   ↙         ↘
 Yes          No
  ↓            ↓
Generate      Save
again         URL
```

## Validation

The API validates incoming URL requests before processing them.

For example:

```json
{
  "originalUrl": ""
}
```

returns a `400 Bad Request` response instead of creating an invalid URL.

Validation errors are handled centrally using `GlobalExceptionHandler`.

## Exception Handling

The application uses `@RestControllerAdvice` for centralized exception handling.

Currently handled cases include:

- Short URL not found
- Invalid request data

Example error response:

```json
{
  "timestamp": "2026-10-03T20:15:00",
  "status": 404,
  "error": "Not Found",
  "message": "Short URL not found"
}
```

## Configuration

Database configuration uses environment variables instead of storing credentials directly in the source code.

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Example local environment variables:

```env
DB_URL=jdbc:mysql://localhost:3306/url_shortener
DB_USERNAME=root
DB_PASSWORD=your_password
```

For deployment, these values can be configured through the hosting provider's environment variable settings.

> Never commit real database credentials or `.env` files to GitHub.

## Running Locally

### Prerequisites

Make sure you have:

- Java 17
- Maven
- MySQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/souravsaha5703/urlshortener.git
cd urlshortener
```

### 2. Create the database

Create a MySQL database:

```sql
CREATE DATABASE url_shortener;
```

### 3. Configure environment variables

Set:

```env
DB_URL=jdbc:mysql://localhost:3306/url_shortener
DB_USERNAME=root
DB_PASSWORD=your_password
```

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## Testing the API

You can use **Postman**, **Insomnia**, or any API client to test the endpoints.

### Create a short URL

```http
POST http://localhost:8080/api/urls
Content-Type: application/json
```

Body:

```json
{
  "originalUrl": "https://github.com"
}
```

### Open the generated short URL

```http
GET http://localhost:8080/{shortCode}
```

### View analytics

```http
GET http://localhost:8080/api/urls/{shortCode}/analytics
```

## Deployment

The application is containerized using Docker and can be deployed to platforms such as Render.

The Docker setup uses:

```text
Java 17
   ↓
Maven Build
   ↓
Spring Boot JAR
   ↓
Java 17 Runtime
```

Database credentials are provided through environment variables during deployment.

## Future Improvements

Possible future improvements include:

- Transaction management for click tracking
- Pagination for analytics
- Authentication and user accounts
- URL expiration
- Redis caching
- Rate limiting
- Swagger/OpenAPI documentation
- Advanced click analytics
- Unit and integration tests

## Learning Outcomes

This project helped in understanding:

- Spring Boot REST API development
- Spring MVC controllers
- Service-layer architecture
- Spring Data JPA
- Hibernate ORM
- Entity relationships using `@ManyToOne`
- MySQL database integration
- DTOs
- Bean validation
- Centralized exception handling
- REST API design
- Docker-based deployment
- Environment-based configuration

## License

This project is intended for learning and portfolio purposes.