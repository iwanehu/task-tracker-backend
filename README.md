# ✅ TaskTracker

TaskTracker is a full-stack task management application built with **Spring Boot**, **React**, and **PostgreSQL**.

The application allows users to create an account, authenticate securely using JWT, and manage their own tasks with different priorities, statuses, filters, and sorting options.

The frontend and backend are deployed independently using **Docker**, **Nginx**, and HTTPS.

## 🌐 Live Demo

### Frontend
https://tasks.164-132-187-235.sslip.io

### API Documentation
https://task.164-132-187-235.sslip.io/swagger-ui/index.html

### OpenAPI
https://task.164-132-187-235.sslip.io/v3/api-docs

---

## ✨ Features

- User registration
- User login
- JWT authentication
- Protected routes
- Multi-user task management
- Create tasks
- Update tasks
- Delete tasks
- Task status management
- Task priority management
- Filter tasks by status
- Filter tasks by priority
- Sort tasks
- PostgreSQL persistence
- RESTful API
- Swagger / OpenAPI documentation
- Responsive React frontend
- Dockerized deployment
- Nginx reverse proxy
- HTTPS with Let's Encrypt

---

## 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- PostgreSQL
- Bean Validation
- Maven
- Swagger / OpenAPI

### Frontend

- React
- Vite
- Axios
- React Router
- JavaScript
- CSS

### Infrastructure

- Docker
- Docker Compose
- Nginx
- Let's Encrypt / Certbot
- Linux VPS

---

## 🏗️ Architecture

```text
                        Internet
                           │
                           ▼
                    ┌─────────────┐
                    │    Nginx    │
                    │    HTTPS    │
                    └──────┬──────┘
                           │
             ┌─────────────┴─────────────┐
             │                           │
             ▼                           ▼
┌────────────────────────┐    ┌─────────────────────────┐
│     React Frontend     │    │   Spring Boot Backend  │
│                        │    │                         │
│ tasks.*.sslip.io       │───▶│ task.*.sslip.io        │
│ Docker + Nginx         │    │ Docker :8083           │
└────────────────────────┘    └────────────┬────────────┘
                                          │
                                          ▼
                               ┌───────────────────────┐
                               │      PostgreSQL       │
                               │                       │
                               │ Users + Tasks         │
                               └───────────────────────┘
```

---

## 🔐 Authentication

TaskTracker uses **JWT (JSON Web Tokens)** for authentication.

After successful login, the backend returns a JWT token.

The frontend stores the token and sends it with protected requests using:

```http
Authorization: Bearer <token>
```

Spring Security validates the token before allowing access to protected endpoints.

---

## 📋 Task Model

Each task contains information such as:

```json
{
  "id": 1,
  "title": "Finish TaskTracker",
  "description": "Deploy the full-stack application",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "createdAt": "2026-09-21T20:00:00",
  "updateAt": "2026-09-21T20:30:00"
}
```

### Task Status

```text
PENDING
IN_PROGRESS
COMPLETED
```

### Task Priority

```text
LOW
MEDIUM
HIGH
```

---

## 🔌 API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate user |

### Tasks

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | Get authenticated user's tasks |
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |

Protected endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## 📁 Project Structure

The project is separated into two repositories.

### Frontend

```text
task-tracker-font/
├── public/
├── src/
│   ├── api/
│   ├── components/
│   ├── pages/
│   └── services/
├── Dockerfile
├── docker-compose.yml
├── package.json
└── vite.config.js
```

Repository:

https://github.com/iwanehu/task-tracker-font

### Backend

```text
task-tracker-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── task_tracker/demo/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── model/
│       │       ├── repository/
│       │       └── service/
│       └── resources/
│           └── application.properties
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

Repository:

https://github.com/iwanehu/task-tracker-backend

---

## 🚀 Running Locally

### Requirements

Make sure you have installed:

- Java 21
- Node.js 20+
- pnpm
- Docker
- Docker Compose

---

## Backend

Clone the repository:

```bash
git clone https://github.com/iwanehu/task-tracker-backend.git
cd task-tracker-backend
```

Create a `.env` file:

```env
POSTGRES_PASSWORD=your_secure_password
```

Start the application:

```bash
docker compose up -d --build
```

The backend will be available at:

```text
http://localhost:8083
```

Swagger:

```text
http://localhost:8083/swagger-ui/index.html
```

---

## Frontend

Clone the frontend:

```bash
git clone https://github.com/iwanehu/task-tracker-font.git
cd task-tracker-font
```

Create or update `.env`:

```env
VITE_API_URL=http://localhost:8083
```

Install dependencies:

```bash
pnpm install
```

Start development server:

```bash
pnpm run dev
```

The application will normally be available at:

```text
http://localhost:5173
```

---

## 🐳 Production Deployment

The production environment uses Docker containers.

```text
Frontend
127.0.0.1:8084 → Docker Nginx → React

Backend
127.0.0.1:8083 → Spring Boot

Database
PostgreSQL Docker container
```

The public traffic is handled by the VPS Nginx reverse proxy.

```text
https://tasks.164-132-187-235.sslip.io
        │
        └──▶ 127.0.0.1:8084

https://task.164-132-187-235.sslip.io
        │
        └──▶ 127.0.0.1:8083
```

TLS certificates are managed with **Let's Encrypt / Certbot**.

---

## 🔄 Deployment Workflow

After pushing changes to GitHub:

### Backend

```bash
cd ~/apps/task-tracker-backend

git pull

docker compose up -d --build
```

### Frontend

```bash
cd ~/apps/task-tracker-font

git pull

docker compose up -d --build
```

---

## 🔒 Security

The application includes:

- Password hashing with BCrypt
- JWT authentication
- Stateless Spring Security configuration
- Protected API endpoints
- Per-user task access
- CORS configuration
- HTTPS
- PostgreSQL running inside the Docker network
- Backend exposed locally behind Nginx

---

## 🗄️ Database

TaskTracker uses PostgreSQL.

Main tables:

```text
users
tasks
```

Each task is associated with the authenticated user.

```text
User
 │
 └──── Task
       ├── title
       ├── description
       ├── status
       ├── priority
       ├── createdAt
       └── updateAt
```

---

## 🧪 API Documentation

Interactive Swagger documentation is available at:

https://task.164-132-187-235.sslip.io/swagger-ui/index.html

OpenAPI specification:

https://task.164-132-187-235.sslip.io/v3/api-docs

---

## 📸 Screenshots

### Dashboard

Add your TaskTracker dashboard screenshot here:

```markdown
![TaskTracker Dashboard](./docs/tasktracker-dashboard.png)
```

---

## 🔮 Future Improvements

Potential improvements include:

- Flyway database migrations
- Automated tests with JUnit and Mockito
- Testcontainers for PostgreSQL integration tests
- Pagination
- Advanced task search
- Due dates
- User profile management
- Refresh tokens
- CI/CD with GitHub Actions
- Custom production domain
- Monitoring and application health checks

---

## 👨‍💻 Author

Developed by **iwanehu**

GitHub:

https://github.com/iwanehu

---

## 📄 License

This project is intended for educational and portfolio purposes.
