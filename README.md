# Quiz Management System

This project contains both backend (Spring Boot) and frontend (React) components for a quiz management system.

## Prerequisites

- Java 17 or higher
- Node.js 14 or higher
- PostgreSQL database
- Maven

## Database Setup

1. Create a PostgreSQL database named `quiz_system`
2. Update the database credentials in `src/main/resources/application.yaml` if needed

## Running the Application

### Option 1: Run Backend and Frontend Separately

1. **Start the Backend:**
   ```bash
   # In the root directory
   mvn spring-boot:run
   ```
   The backend will run on http://localhost:8080

2. **Start the Frontend:**
   ```bash
   # In the frontend directory
   cd frontend
   npm install
   npm start
   ```
   The frontend will run on http://localhost:3000

### Option 2: Using Scripts (Windows)

1. **Run Backend:**
   ```bash
   run-backend.bat
   ```

2. **Run Frontend:**
   ```bash
   cd frontend
   run-frontend.bat
   ```

## Features

### Backend (Spring Boot)
- REST API for quiz management
- PostgreSQL database integration
- Admin endpoints for managing questions
- User endpoints for taking quizzes

### Frontend (React)
- User interface for taking quizzes
- Admin panel for managing questions
- Responsive design
- Real-time quiz submission and scoring

## API Endpoints

- `GET /api/quiz/questions` - Get quiz questions
- `POST /api/quiz/submit` - Submit quiz answers
- `GET /api/quiz/admin/questions` - Get all questions (admin)
- `POST /api/quiz/admin/questions` - Add question (admin)
- `DELETE /api/quiz/admin/questions/{id}` - Delete question (admin)

## Project Structure

```
task-management-system-java/
├── frontend/          # React frontend
├── src/              # Spring Boot backend source
├── pom.xml           # Maven configuration
├── mvnw              # Maven wrapper
└── README.md         # This file
```