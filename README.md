# Country City Service

Simple Spring Boot backend service for countries and cities with Swagger/OpenAPI documentation.

## Requirements

- Java 21
- Maven 3.9+
- Git
- API client such as Postman, Bruno, or curl

## Run

```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8080`.

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

The generated OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Endpoints

| Method | Path | Description |
| --- | --- | --- |
| GET | `/countries` | List all countries |
| GET | `/countries/{countryId}/cities?page=0&size=10` | List cities for a selected country with pagination |
| GET | `/cities/{cityId}` | Get city details by id |

## Example Requests

```bash
curl http://localhost:8080/countries
curl "http://localhost:8080/countries/1/cities?page=0&size=2"
curl http://localhost:8080/cities/101
```

## Test

```bash
mvn test
```

## Data Storage

This implementation uses in-memory hardcoded data. No database or Docker setup is required.
