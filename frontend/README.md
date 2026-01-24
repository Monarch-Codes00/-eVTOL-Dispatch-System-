# Quiz Management System - Frontend

This is the React frontend for the Quiz Management System. It provides both user and admin interfaces for taking quizzes and managing questions.

## Features

### User Interface
- Take quizzes with multiple choice questions
- Navigate through questions with previous/next buttons
- View quiz results with score and percentage
- Restart quiz functionality

### Admin Interface
- View all existing questions
- Add new questions with 4 options and correct answer
- Delete questions
- Refresh question list

## Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- Backend server running on http://localhost:8080

## Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

## Running the Application

1. Make sure the backend is running on port 8080
2. Start the React development server:
   ```bash
   npm start
   ```
3. Open [http://localhost:3000](http://localhost:3000) in your browser

## API Integration

The frontend connects to the backend API at `/api/quiz`:

- `GET /api/quiz/questions` - Get questions for quiz
- `POST /api/quiz/submit` - Submit quiz answers
- `GET /api/quiz/admin/questions` - Get all questions (admin)
- `POST /api/quiz/admin/questions` - Add new question (admin)
- `DELETE /api/quiz/admin/questions/{id}` - Delete question (admin)

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Admin.js
│   │   └── Quiz.js
│   ├── services/
│   │   └── quizApi.js
│   ├── App.js
│   ├── App.css
│   ├── index.js
│   └── index.css
└── package.json
```

## Technologies Used

- React 18
- React Router DOM
- CSS for styling
- Fetch API for HTTP requests