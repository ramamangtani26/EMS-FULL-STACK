# Employee Management System (EMS) — Full Stack

A full-stack web application for managing employees and departments, built as a portfolio project to demonstrate end-to-end development — from a relational database and REST API to a deployed, responsive frontend.

**Live Demo:** [ems-full-stack-nine.vercel.app](https://ems-full-stack-nine.vercel.app)

---

## 🚀 Features

- **Employee Management** — add, view, search, filter, and manage employee records (name, contact info, address, salary, joining date, department)
- **Department Management** — create and organize departments
- **Dashboard Insights** — total employees, highest/lowest/average salary, department-wise employee count
- **Search & Filter** — filter employees by name, department, and salary range
- **Authentication** — simple login gate to access the dashboard
- **REST API** — documented with Swagger/OpenAPI
- **Responsive UI** — clean, modern interface built with React

---

## 🛠️ Tech Stack

**Backend**
- Java 17
- Spring Boot 3.3.4
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Springdoc OpenAPI (Swagger UI)

**Frontend**
- React (Vite)
- Axios
- React Router

**Deployment**
- Backend: [Render](https://render.com) (Docker)
- Frontend: [Vercel](https://vercel.com)
- Database: Render PostgreSQL

---

## 📁 Project Structure

```
EMS-FULL-STACK/
├── ems-backend/          # Spring Boot REST API
│   ├── src/main/java/com/ems/
│   │   ├── config/       # CORS & OpenAPI configuration
│   │   ├── controller/   # REST controllers (Auth, Employee, Department)
│   │   ├── dto/          # Data transfer objects
│   │   ├── entity/       # JPA entities
│   │   ├── exception/    # Global exception handling
│   │   ├── mapper/       # Entity ↔ DTO mappers
│   │   ├── repository/   # Spring Data JPA repositories
│   │   └── service/      # Business logic
│   └── src/main/resources/
│       └── application.properties
│
└── ems-frontend/         # React + Vite SPA
    └── src/
        ├── api/          # Axios client & API calls
        ├── components/   # Reusable UI components
        ├── context/      # React context (auth state, etc.)
        └── pages/         # Page-level components
```

---

## ⚙️ Running Locally

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL (running locally or a cloud instance)

### Backend Setup

```bash
cd ems-backend

# Set the following environment variables (or update application.properties directly)
DB_URL=jdbc:postgresql://localhost:5432/your_db_name
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
APP_ADMIN_USERNAME=admin
APP_ADMIN_PASSWORD=your_chosen_password

# Run
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080`.
Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

### Frontend Setup

```bash
cd ems-frontend
npm install

# Create a .env file (see .env.example) with:
VITE_API_BASE_URL=http://localhost:8080/api

npm run dev
```

The app will start on `http://localhost:5173`.

---

## 🔑 Demo Login

Login credentials are configured via environment variables on the backend (`APP_ADMIN_USERNAME` / `APP_ADMIN_PASSWORD`). Contact the repo owner for demo access, or set your own via the environment variables above when running locally.

> **Note:** This project uses a simplified, demo-level login for portfolio purposes (a single admin credential check) rather than full authentication with hashed passwords and JWTs — intentionally kept lightweight since the focus is the CRUD/API/deployment pipeline. A production version would use Spring Security with hashed credentials and a proper Users table.

---

## 📌 API Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Log in with admin credentials |
| GET | `/api/employees` | List all employees |
| POST | `/api/employees` | Add a new employee |
| GET | `/api/departments` | List all departments |
| POST | `/api/departments` | Add a new department |

Full interactive API documentation is available via Swagger UI once the backend is running.

---

## 📄 License

This project is open source and available for learning purposes.

---

## 👤 Author

**Rama Mangtani**
B.Tech CSE (Gaming Specialization), VIT — 2023–2027
