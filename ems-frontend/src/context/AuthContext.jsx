import { createContext, useContext, useState } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem('ems_token'));
  const [username, setUsername] = useState(localStorage.getItem('ems_username'));

  const loginUser = (newToken, newUsername) => {
    localStorage.setItem('ems_token', newToken);
    localStorage.setItem('ems_username', newUsername);
    setToken(newToken);
    setUsername(newUsername);
  };

  const logoutUser = () => {
    localStorage.removeItem('ems_token');
    localStorage.removeItem('ems_username');
    setToken(null);
    setUsername(null);
  };

  return (
    <AuthContext.Provider value={{ token, username, loginUser, logoutUser }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
