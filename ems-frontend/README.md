# Employee Management System — Frontend (React + Vite)

## Setup
```
npm install
cp .env.example .env      # point VITE_API_BASE_URL at your backend
npm run dev
```
App runs at `http://localhost:5173`. Demo login: `admin / admin123` (matches the backend's
`app.admin.*` properties).

## Structure
```
src/
├── api/          axios client + one file per resource (employees, departments, auth)
├── context/      AuthContext (demo token stored in localStorage)
├── components/   Navbar, EmployeeTable, EmployeeFormModal, StatisticsPanel, ProtectedRoute
└── pages/        Login, Dashboard (employees), Departments
```

## Build for production
```
npm run build   # outputs to dist/
```
