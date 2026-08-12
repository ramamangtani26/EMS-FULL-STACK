import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { username, logoutUser } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logoutUser();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">EMS</div>
      <div className="navbar-links">
        <NavLink to="/" end className={({ isActive }) => (isActive ? 'active' : '')}>
          Employees
        </NavLink>
        <NavLink to="/departments" className={({ isActive }) => (isActive ? 'active' : '')}>
          Departments
        </NavLink>
      </div>
      <div className="navbar-user">
        <span>{username}</span>
        <button className="btn btn-ghost" onClick={handleLogout}>Logout</button>
      </div>
    </nav>
  );
}
