# QuizApp - Spring Boot Quiz Application

A Spring Boot application for creating and managing quizzes. This application provides a platform to create quizzes with multiple-choice questions, manage questions and their options, and support various difficulty levels and categories.

## Technology Stack

- **Java 24**
- **Spring Boot 3.4.5**
- **Spring Data JPA**
- **Thymeleaf** for server-side templating
- **MySQL** for database
- **Lombok** for reducing boilerplate code
- **Maven** for dependency management

## Project Structure

```
src/main/
├── java/com/app/
│   ├── controller/    # REST endpoints and MVC controllers
│   ├── model/         # JPA entity classes
│   ├── repository/    # Data access interfaces
│   ├── service/       # Business logic implementation
│   └── starter/       # Application bootstrap classes
└── resources/
    ├── application.properties  # Application configuration
    ├── static/                 # Static web resources (CSS, JS)
    └── templates/              # Thymeleaf HTML templates
```

## Data Model

The application is built around three primary entities with the following relationships:

- **Quiz**: The top-level entity that contains questions
  - Properties: id, name
  - Relationships: One-to-many with Question

- **Question**: Represents individual quiz questions
  - Properties: id, text, category, difficulty
  - Relationships: Many-to-one with Quiz, One-to-many with Options

- **Options**: Represents answer choices for questions
  - Properties: id, text, isCorrect
  - Relationships: Many-to-one with Question

## Getting Started

### Prerequisites

- JDK 24 or later
- MySQL 8.0 or later
- Maven 3.6 or later

### Database Setup

1. Create a MySQL database named `quizapp`
2. Configure the database connection in `application.properties` (default values shown):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizapp
spring.datasource.username=root
spring.datasource.password=password
```

### Running the Application

1. Clone the repository
2. Navigate to the project root directory
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Access the application at: http://localhost:8080

## Features

- Create, read, update, and delete quizzes
- Add and manage questions with multiple difficulty levels
- Categorize questions for better organization
- Create multiple-choice options for each question
- Mark correct answers for automatic scoring

## API Reference

The application provides REST endpoints for managing quizzes, questions, and options:

- `/api/quizzes` - Quiz management
- `/api/questions` - Question management
- `/api/options` - Options management

Detailed API documentation will be available after implementation.

## Future Roadmap

- User authentication and role-based access control
- Quiz-taking functionality with scoring
- Timer functionality for timed quizzes
- Results tracking and analytics
- Mobile-responsive UI improvements

## Contributing

Contributions are welcome! To contribute:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
